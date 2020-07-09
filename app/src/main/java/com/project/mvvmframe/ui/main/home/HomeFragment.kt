package com.project.mvvmframe.ui.main.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.CountDownTimer
import androidx.lifecycle.ViewModelProvider
import com.project.mvvmframe.R
import com.project.mvvmframe.base.BaseVMFragment
import com.project.mvvmframe.ui.main.MainVM
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * @CreateDate 2020/5/27 9:30
 * @Author jaylm
 */
class HomeFragment : BaseVMFragment<MainVM>() {
    private var mTimer: CountDownTimer? = null
    private var isChecked = false

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    //    override fun providerVMClass() = MainVM::class.java
    override fun bindLayout(): Int = R.layout.fragment_home
    override fun initVM() = ViewModelProvider(requireActivity()).get(MainVM::class.java)


    override fun initView() {
    }


    override fun setListener() {
        super.setListener()
        tv_pre.setOnClickListener {
//            mViewModel.changePosition(3)
            praiseView.setNum((Math.random() * 100).toInt())
        }
        tv_next.setOnClickListener {
            mViewModel.changePosition(1)
        }

        tv_voice.setOnClickListener {
            startActivity(Intent(activity, VoiceActivity::class.java))
        }

        tv_animator.setOnClickListener {
            startActivity(Intent(activity, AnimatorActivity::class.java))
        }

        ll_praise.setOnClickListener {
            isChecked = !isChecked
            iv_praise.setImageResource(if (isChecked) R.mipmap.icon_praise_check else R.mipmap.icon_praise_uncheck)
            praiseView.startAnimal(praiseView.getNum() + if (isChecked) 1 else -1, 1000L)
            val scaleX = ObjectAnimator.ofFloat(iv_praise, "scaleX", 1f, 1.5f, 1f)
            val scaleY = ObjectAnimator.ofFloat(iv_praise, "scaleY", 1f, 1.5f, 1f)
            val animator = AnimatorSet()
            animator.play(scaleX).with(scaleY)
            animator.setDuration(1000L).start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTimer != null) {
            mTimer?.cancel()
        }
    }
}