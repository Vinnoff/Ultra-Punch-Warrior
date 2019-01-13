package balekouy.industries.punchwarrior.presentation.levelselection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import balekouy.industries.punchwarrior.R
import balekouy.industries.punchwarrior.data.models.Level
import balekouy.industries.punchwarrior.presentation.BaseActivity
import kotlinx.android.synthetic.main.activity_level_selection.*

class LvlSelectActivity : BaseActivity(R.layout.activity_level_selection), LvlSelectAdapter.Listener {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LvlSelectActivity::class.java)
        }

        private const val FIGHTER_PER_LINES = 4
    }

    private lateinit var viewModel: LvlSelectViewModel
    private lateinit var adapter: LvlSelectAdapter


    override fun initUI() {
        lvl_select_back.setOnClickListener { onBackPressed() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(baseContext, FIGHTER_PER_LINES)
        lvl_select_recycler_view.layoutManager = gridLayoutManager
        lvl_select_recycler_view.adapter = LvlSelectAdapter(baseContext, mutableListOf(), this)
        adapter = lvl_select_recycler_view.adapter as LvlSelectAdapter
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(LvlSelectViewModel::class.java)
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

        viewModel.getLiveDataLevels().observe(this, Observer { data ->
            if (data != null && data.isNotEmpty()) setupRecyclerView(data.map { it.second })
            else showEmptyList()
        })
    }

    private fun setupRecyclerView(data: List<Level>) {
        adapter.data = data
    }

    override fun onLevelClick(position: Int, level: Level, transitionName: String, transitionView: View) {
        startActivity(
            LvlDescriptionActivity.newIntent(baseContext, level, transitionName),
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, transitionView, transitionName).toBundle()
        )
    }

    override fun onLevelLocked(position: Int) {
        Toast.makeText(baseContext, getString(R.string.level_locked_info), Toast.LENGTH_LONG).show()
    }

    override fun onLevelError(position: Int) {

    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}