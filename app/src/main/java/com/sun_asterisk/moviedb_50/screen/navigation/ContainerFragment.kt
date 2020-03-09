package com.sun_asterisk.moviedb_50.screen.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.screen.home.HomeFragment

class ContainerFragment : Fragment() {
    private var startingPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_container, container, false)
        loadFragment(HomeFragment(), startingPosition)
        return view
    }

    private fun loadFragment(fragment: Fragment, position: Int): Boolean {
        when {
            startingPosition > position -> {
                activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                        R.anim.left_to_right,
                        R.anim.exit_left_to_right,
                        R.anim.right_to_left,
                        R.anim.exit_right_to_left
                    )?.replace(R.id.containerFramelayout, fragment)
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ?.commit()
            }
            startingPosition < position -> {
                activity?.supportFragmentManager?.beginTransaction()?.setCustomAnimations(
                        R.anim.right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.left_to_right,
                        R.anim.exit_left_to_right
                    )?.replace(R.id.containerFramelayout, fragment)
                    ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    ?.commit()
            }
            else -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.containerFramelayout, fragment)
                    ?.commit()
            }
        }
        startingPosition = position
        return true
    }
}
