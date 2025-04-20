package com.github.kongpf8848.viewworld.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.github.kongpf8848.viewworld.base.BaseFragment
import com.github.kongpf8848.viewworld.databinding.FragmentTitleBinding

class TitleFragment : BaseFragment() {

    private lateinit var binding: FragmentTitleBinding

    companion object {
        fun newInstance(title: String): TitleFragment {
            return TitleFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTitleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSubject.text = arguments?.getString("title", "")
    }

}