package com.project.rankers.adprer.model

interface SectionsPagerModel {
    fun setListItem(position: Int)

    fun getListItem(position: Int): Int

    fun getCount(): Int

}