package com.project.rankers.adapter.model

interface SectionsPagerModel {
    fun setListItem(position: Int)

    fun getListItem(position: Int): Int

    fun getCount(): Int

}