package com.matheus.githubhelper.services

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.matheus.githubhelper.MainActivity
import com.matheus.githubhelper.R
import com.matheus.githubhelper.api.GithubAPI
import com.matheus.githubhelper.models.Commit
import com.matheus.githubhelper.models.FavoritedRepository
import com.matheus.githubhelper.persistence.Banco

class SyncService: Service() {

    private val helper = GithubAPI()
    private val banco = Banco(this)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        checarRepositorios(banco.listFavoritedRepositories())
        return super.onStartCommand(intent, flags, startId)
    }

    private fun checarRepositorios(lista: ArrayList<FavoritedRepository>) {
        if(lista.size <= 0)
            return
        val rep = lista[0]
        lista.remove(rep)
        checarRepositorio(rep) { checarRepositorios(lista) }
    }

    private fun checarRepositorio(rep: FavoritedRepository, cb: () -> Unit) {
        helper.buscarCommits(rep.full_name) {sucesso, resposta, _ ->
            if(sucesso && resposta != null) {
                val commit: Commit = resposta[0]
                if(rep.commit_id != commit.id) {
                    if(rep.commit_id != "") {
                        notificar(rep.full_name)
                    }
                    rep.commit_id = commit.id
                    banco.updateFavoritedRepository(rep)
                }
            }
            cb()
        }
    }

    private fun notificar(full_name: String) {
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, "CommitsServiceChannel")
            .setContentTitle("Atualização")
            .setContentText("O repositório $full_name tem novos commits")
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "CommitsServiceChannel",
                "Commits Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

}