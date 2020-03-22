package com.sun_asterisk.moviedb_50.screen.navigation

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sun_asterisk.moviedb_50.R
import com.sun_asterisk.moviedb_50.screen.MainActivity
import com.sun_asterisk.moviedb_50.screen.favorite.FavoriteFragment
import com.sun_asterisk.moviedb_50.screen.home.HomeFragment
import com.sun_asterisk.moviedb_50.screen.search.SearchFragment
import com.sun_asterisk.moviedb_50.utils.showSnackBar
import kotlinx.android.synthetic.main.fragment_container.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ContainerFragment : Fragment() {
    private var startingPosition = 0
    private var changeMenu = true

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
        view.bottomNavigation.setOnNavigationItemSelectedListener(selectedListener)
        initToolBar()
        loadFragment(HomeFragment(), startingPosition)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (changeMenu) {
            inflater.inflate(R.menu.search_view_home, menu)
        } else {
            activity?.run {
                inflater.inflate(R.menu.search_view_favorite, menu)
                val manager = supportFragmentManager
                val fragment =
                    manager.findFragmentById(R.id.containerFrameLayout)
                if (fragment is FavoriteFragment) {
                    val searchView =
                        menu.findItem(R.id.ic_searchView_favorite).actionView as SearchView
                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                        override fun onQueryTextChange(newText: String): Boolean {
                            fragment.favoriteAdapter?.search(newText) {
                            }
                            return true
                        }

                        override fun onQueryTextSubmit(query: String): Boolean {
                            fragment.favoriteAdapter?.search(query) {
                                activity?.showSnackBar(R.string.nothing_found)
                            }
                            return true
                        }
                    })
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_searchView_home) {
            activity?.run {
                supportFragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.left_to_right,
                        R.anim.exit_left_to_right
                    ).replace(R.id.mainFrameLayout, SearchFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        return false
    }

    private fun initToolBar() {
        view?.toolbar?.let {
            (activity as? MainActivity)?.run { setSupportActionBar(it) }
        }
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
                    changeMenu = true
                }
                startingPosition < position -> {
                    supportFragmentManager.beginTransaction().setCustomAnimations(
                            R.anim.right_to_left,
                            R.anim.exit_right_to_left,
                            R.anim.left_to_right,
                            R.anim.exit_left_to_right
                        ).replace(R.id.containerFrameLayout, fragment)
                        .commit()
                    changeMenu = false
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.containerFrameLayout, fragment)
                        .commit()
                }
            }
            invalidateOptionsMenu()
        }
        startingPosition = position
        return true
    }

    private val selectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment(), 1)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    loadFragment(FavoriteFragment(), 2)
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        }
}