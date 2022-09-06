package com.example.expensestracker

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.data.MyData
import com.example.expensestracker.databinding.ActivityMainBinding
import com.example.expensestracker.databinding.PopUpLayoutBinding
import com.example.expensestracker.myDataBase.MySQLiteDB
import com.example.expensestracker.popUpDialog.AddUpdateDialog
import com.example.expensestracker.popUpDialog.FilterDateDialog
import com.example.expensestracker.recyclerViewAdapter.MyRVAdapter
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myRVAdapter: MyRVAdapter

    private val myDB = MySQLiteDB(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myRVAdapter = MyRVAdapter(this)
        binding.recordsRV.adapter = myRVAdapter
        binding.recordsRV.layoutManager = LinearLayoutManager(this)

        loadAllData(myRVAdapter)

        registerForContextMenu(binding.menuBtn)

        binding.addRecordFAB.setOnClickListener {
            val addUpdateDialog = AddUpdateDialog(this, this, null, { dialog, binding, time ->
                addRecord(binding, time)
                Toast.makeText(this, "Add Clicked", Toast.LENGTH_SHORT).show()
                loadAllData(myRVAdapter)
                dialog.stopLoading()
            }, {
                it.stopLoading()
            })
            addUpdateDialog.startLoading()
        }

//        binding.menuBtn.setOnClickListener {
//            val dialog = FilterDateDialog(this, this)
//            dialog.startLoading()
//            Toast.makeText(this, "Menu Clicked", Toast.LENGTH_SHORT).show()
//        }

        binding.searchBox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var queryTxt = p0 ?: ""
                queryTxt = "%$queryTxt%"
                loadSearchData(queryTxt, myRVAdapter)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                onQueryTextSubmit(p0)
                return false
            }

        })

        // Adding Gesture Control In RecyclerView
        var lastDeletedData: MyData?
        var lastDeletedDataIndex = -1

        val simpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.bindingAdapterPosition

                lastDeletedData = myRVAdapter.getDataAtIndex(index)
                lastDeletedDataIndex = index
                myRVAdapter.removeDataFromIndex(index)

                lastDeletedData?.let { myDB.deleteRecord(it) }

                Snackbar.make(binding.recordsRV, "One record is deleted !", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        lastDeletedData?.also {
                            myRVAdapter.addDataAtIndex(lastDeletedDataIndex, it)
                            myDB.addNewRecord(MyData.getContentValues(it))
                        }
                    }
                    .show()
            }

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean,
            ) {
                val decorator = getSwipeDecorator(c, recyclerView, viewHolder,
                    dX, dY, actionState, isCurrentlyActive)
                decorator.create().decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX,
                    dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recordsRV)
    }

    private fun loadSearchData(queryTxt: String, adapter: MyRVAdapter) {
        val searchedRecords = ArrayList<MyData>()
        val cursor = myDB.readSearchedData(queryTxt) ?: return

        while (cursor.moveToNext()) {
            val data = MyData(cursor)
            searchedRecords.add(data)
        }
        adapter.setData(searchedRecords)
    }

    private fun loadAllData(adapter: MyRVAdapter) {
        val allRecord = ArrayList<MyData>()
        val cursor = myDB.readAllData() ?: return

        while (cursor.moveToNext()) {
            val data = MyData(cursor)
            allRecord.add(data)
        }
        adapter.setData(allRecord)
    }

    private fun addRecord(dialogBinding: PopUpLayoutBinding, time: String) {
        val cv = MyData.getContentValues(dialogBinding, time)
        cv?.also { myDB.addNewRecord(it) }
    }

    fun getSwipeDecorator(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean,
    ): RecyclerViewSwipeDecorator.Builder {
        return RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder,
            dX, dY, actionState, isCurrentlyActive)
            .addSwipeLeftLabel("Delete")
            .setSwipeLeftLabelColor(Color.WHITE)
            .setSwipeLeftLabelTextSize(1, 20.0f)
            .addSwipeRightLabel("Delete")
            .setSwipeRightLabelColor(Color.WHITE)
            .setSwipeRightLabelTextSize(1, 20.0f)
            .addCornerRadius(1, 10)
            .addBackgroundColor(Color.RED)
            .addActionIcon(R.drawable.delete_icon)
    }

    // Creating The Menu Button
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.home_menu, menu)
        Toast.makeText(this, "Menu Created", Toast.LENGTH_SHORT).show()
        println("Menu Created")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filterDateMenuBtn -> {
                val dialog = FilterDateDialog(this, this)
                dialog.startLoading()
                return true
            }
            R.id.deleteAllMenuBtn -> {
                Toast.makeText(this, "All Items Has Been Selected", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }
}