package com.matheus.githubhelper.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.matheus.githubhelper.R

class RemoverDialog: DialogFragment() {

    private var listener: OnRepositoryTrashSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Deseja apagar ?")

        builder.setPositiveButton("Sim") { dialog, which ->
            if (listener != null) {
                listener!!.onRepositoryTrashSet(true)
            }

        }
        builder.setNegativeButton("Não") { dialog, which ->
            dismiss()
        }

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_remover, null)

        builder.setView(view)

        return builder.create()
    }

    interface OnRepositoryTrashSetListener  {

        fun onRepositoryTrashSet(boo: Boolean) {

        }

    }

    companion object {
        fun show(fm: FragmentManager, listener: OnRepositoryTrashSetListener) {
            val dialog = RemoverDialog()
            dialog.listener = listener
            dialog.show(fm, "repositoryNameDialog")
        }
    }
}