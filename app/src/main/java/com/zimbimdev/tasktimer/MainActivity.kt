package com.zimbimdev.tasktimer

import android.content.ContentValues
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked {

//whether the activity is in 2-pane mode
    private var mTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mTwoPane = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if(fragment != null) {
            //there was an existing fragment to edit task, make sure panes are set correctly
           showEditPane()
        } else {
            task_details_container.visibility = if(mTwoPane) View.INVISIBLE else View.GONE
            mainFragment.view?.visibility = View.VISIBLE
        }
    }

    private fun showEditPane() {
        task_details_container.visibility = View.VISIBLE
        // hide the left pane if in single pane view
        mainFragment.view?.visibility = if(mTwoPane) View.VISIBLE else View.GONE
    }

    private fun removeEditPane(fragment: Fragment? = null) {
        Log.d(TAG, "removeEditPane call")
        if(fragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }

        //set the visibility for the right hand pane
        task_details_container.visibility = if(mTwoPane) View.INVISIBLE else View.GONE
            // and show left pane
        mainFragment.view?.visibility = View.VISIBLE


    }

    override fun onSaveClicked() {
        Log.d(TAG, "onSaveClicked called")
        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        removeEditPane(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menumain_task -> taskEditRequest(null)
//            R.id.menumain_settings -> true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskEditRequest(task: Task?) {
        Log.d(TAG, "taskEditRequest starts")

        //create new fragment to edit the task
        val newFragment = AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_details_container, newFragment)
            .commit()

        showEditPane()

        Log.d(TAG, "taskEditRequest ends")
    }
}
