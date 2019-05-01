package com.project.rankers.ui.tournament

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.project.rankers.R
import com.project.rankers.ViewModelProviderFactory
import com.project.rankers.data.model.db.Tournament
import com.project.rankers.data.remote.response.GroupResponse
import com.project.rankers.databinding.ActivityTournamentBinding
import com.project.rankers.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject

class TournamentActivity : BaseActivity<ActivityTournamentBinding, TournamentViewModel>(), TournamentNavigator, TournamentAdapter.TournamentAdapterListener {
    @Inject
    internal var tournamentAdapter: TournamentAdapter? = null
    @Inject
    internal var factory: ViewModelProviderFactory? = null
    private var mLayoutManager = LinearLayoutManager(this)
    private lateinit var activityTournamentBinding: ActivityTournamentBinding
    private var tournamentViewModel: TournamentViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_tournament
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): TournamentViewModel {
        tournamentViewModel = ViewModelProviders.of(this, factory).get(TournamentViewModel::class.java)
        return tournamentViewModel as TournamentViewModel
    }

    override fun handleError(throwable: Throwable) {
        displayLog("TournamentActivity", throwable.toString())
    }

    override fun updateTournament(tournamentItem: List<Tournament>) {
        tournamentAdapter!!.addItems(tournamentItem)
    }

    override fun uploadTournament() {
       tournamentViewModel!!.uploadTournament(tournamentAdapter!!.getItems()!!)
    }

    override fun showDialog(title: String, message: String) {
        runOnUiThread {
            MaterialDialog(this).show {
                title(text = title)
                message(text =  message)
                positiveButton(text = "확인"){
                    finish()
                }
            }
        }
    }

    override fun onRetryClick() {
        tournamentViewModel!!.fetchTournament()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTournamentBinding = getViewDataBinding()
        tournamentViewModel!!.navigator = this

        setUp()

        val intent = intent
        val id = intent.extras.getString("CONTEST_ID")
        val depart = intent.extras.getString("CONTEST_DEPART")

        tournamentViewModel!!.setContestInfo(id,depart)
    }

    @SuppressLint("WrongConstant")
    private fun setUp() {
        val mToolbar = activityTournamentBinding.toolbar
        setSupportActionBar(mToolbar)
        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        tournamentAdapter = TournamentAdapter(ArrayList<Tournament>())
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityTournamentBinding.recycler.layoutManager = mLayoutManager
        activityTournamentBinding.recycler.itemAnimator = DefaultItemAnimator()
        activityTournamentBinding.recycler.setHasFixedSize(true)
        activityTournamentBinding.recycler.adapter = tournamentAdapter
        tournamentAdapter!!.setListener(this)
    }
}