package balekouy.industries.punchwarrior.presentation.scores

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Score
import balekouy.industries.punchwarrior.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_scores.*

class ScoresActivity : BaseActivity(R.layout.activity_scores) {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ScoresActivity::class.java)
        }
    }

    private lateinit var viewModel: ScoresViewModel
    private lateinit var adapter: ScoresAdapter


    override fun initUI() {
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
                }
                if (viewState.isLoading) {
                    showLoading()
                } else {
                    hideLoading()
                }
            }
        })

        viewModel.getLiveDataScores().observe(this, Observer { listModel ->
            if (listModel != null && listModel.isNotEmpty()) {
                setupRecyclerView(listModel)
            } else {
                showEmptyList()
            }
        })
    }

    private fun setupRecyclerView(listModel: List<Score>) {
        adapter.listModel = listModel
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() {
        Toast.makeText(this, "Loading", Toast.LENGTH_LONG).show()

    }

    private fun hideLoading() {
        Toast.makeText(this, "End Loading", Toast.LENGTH_LONG).show()

    }

    private fun showEmptyList() {
        Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show()
    }

    private fun showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }
}