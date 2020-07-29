package com.killzone.movavitest.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.killzone.movavitest.ui.HabrPostsListFragment
import com.killzone.movavitest.ui.PokemonListFragment

const val HABR_LIST = 0
const val POKEMON_LIST = 1

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments: Map<Int, () -> Fragment> = mapOf(
        HABR_LIST to { HabrPostsListFragment() },
        POKEMON_LIST to { PokemonListFragment() }
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}