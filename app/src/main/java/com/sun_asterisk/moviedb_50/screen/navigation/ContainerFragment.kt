package com.sun_asterisk.moviedb_50.screen.navigation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.screen.MainActivity
import com.sun_asterisk.moviedb_50.screen.home.HomeFragment
import com.sun_asterisk.moviedb_50.screen.search.SearchFragment
import kotlinx.android.synthetic.main.toolbar.view.*

class ContainerFragment : Fragment() {
    private var startingPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initToolBar()
        loadFragment(HomeFragment(), startingPosition)
    }

    private fun loadFragment(fragment: Fragment, position: Int): Boolean {
        activity?.run {
            when {
                startingPosition > position -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.left_to_right,
                            R.anim.exit_left_to_right,
                            R.anim.right_to_left,
                            R.anim.exit_right_to_left
                        ).replace(R.id.containerFrameLayout, fragment)
                        .commit()
                }
                startingPosition < position -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.right_to_left,
                            R.anim.exit_right_to_left,
                            R.anim.left_to_right,
                            R.anim.exit_left_to_right
                        ).replace(R.id.containerFrameLayout, fragment)
                        .commit()
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFrameLayout, fragment)
                        .commit()
                }
            }
        }
        startingPosition = position
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.searchView) {
            activity?.run {
                supportFragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.left_to_right,
                        R.anim.exit_left_to_right
                    ).add(R.id.mainFrameLayout, SearchFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        return false
    }

    private fun initToolBar() {
        view?.toolbar?.let {
            (activity as? MainActivity)?.run { setSupportActionBar(it)
            }
        }
    }
}
