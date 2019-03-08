package balekouy.industries.punchwarrior.presentation.scores

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity : BaseActivity(ScoresActivity::class.java.simpleName, R.layout.activity_scores) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ScoresActivity::class.java)
        }
    }

    private lateinit var viewModel: ScoresViewModel
    private lateinit var adapter: ScoresAdapter


    override fun initUI() {
        startMusic(R.raw.really_not_rocky_victory_sound)
        scores_back.setOnClickListener { onBackPressed() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(baseContext)
        scores_recycler_view.layoutManager = linearLayoutManager
        scores_recycler_view.adapter = ScoresAdapter(baseContext, mutableListOf())
        val dividerItemDecoration = DividerItemDecoration(scores_recycler_view.context, linearLayoutManager.orientation)
        scores_recycler_view.addItemDecoration(dividerItemDecoration)
        adapter = scores_recycler_view.adapter as ScoresAdapter
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ScoresViewModel::class.java)
        viewModel.let { lifecycle.addObserver(it) }
    }

    override fun initObservers() {
        viewModel.getLiveDataState().observe(this, Observer { viewState ->
            viewState?.let {
                when {
                    it.isError -> showError()
                    it.isLoading -> showLoading()
                    else -> hideLoading()
                }
            }
        })

        viewModel.getLiveDataScores().observe(this, Observer { data ->
            if (data != null && data.isNotEmpty()) {
                setupRecyclerView(data)
            } else {
                showEmptyList()
            }
        })
    }

    private fun setupRecyclerView(data: List<Score>) {
        adapter.data = data
    }
}