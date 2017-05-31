package com.drrino.wuapnjieshuhui.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.drrino.wuapnjieshuhui.R
import com.drrino.wuapnjieshuhui.domain.network.snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class ComicFragment(var url: String) : Fragment() {

    lateinit var progressBar: ProgressBar
    lateinit var ivComic: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comic_page, container, false)
        progressBar = view.find(R.id.progressBar)
        ivComic = view.find(R.id.ivComic)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Picasso.with(context).load(url).placeholder(R.color.material_teal_50).into(ivComic,object :Callback{
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError() {
                ivComic.snackbar(R.string.network_error)
            }
        })
    }
}
