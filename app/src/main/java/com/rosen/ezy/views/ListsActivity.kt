package com.rosen.ezy.views

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rosen.ezy.R
import com.rosen.ezy.domain.data.Income
import com.rosen.ezy.utils.EzyPrefs
import com.rosen.ezy.utils.EzyUtils
import kotlinx.android.synthetic.main.lists_activity.*

class ListsActivity : AppCompatActivity() {

    lateinit var mAdapter : ListAdapter
    private val incomeList : MutableList<Income> = mutableListOf()
    private var entryEdition : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lists_activity)

        mAdapter = ListAdapter(applicationContext, incomeList, object : ListAdapter.ListListener {
            override fun deleteItem(income: Income, position: Int) {
                deleteEntry(income, position)
            }

            override fun editItem(income: Income, position: Int) {
                editEntry(income, position)
            }

        })

        list.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        }

        initialSetUp()
        add_entry.setOnClickListener {
            if (add_entry.text.toString().contentEquals("Update")) {
                edit()
            } else {
                addEntry()
            }
        }
    }

    private fun initialSetUp() {
        val currentIncomeListString = EzyPrefs.getPreferences(applicationContext, "incomes")
        val currentIncomeList = currentIncomeListString?.let {
            if (it.isNotEmpty()) {
                EzyUtils.stringToIncomeConversion(
                    it
                ).toMutableList()
            } else {
                mutableListOf()
            }
        }

        currentIncomeList?.let { list ->
            incomeList.clear()
            incomeList.addAll(list)
            computeTotals(list)
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun addEntry() {
        val entry = income_entry.text.toString().trim()
        if (entry.isNotEmpty()) {
            val currentIncomeList = getCurrentList()

            currentIncomeList?.let { list ->
                list.add(Income(entry.toInt(), System.currentTimeMillis()))
                EzyPrefs.setPreferences(applicationContext, EzyUtils.incomeToString(list), "incomes")
                incomeList.clear()
                incomeList.addAll(list)
                computeTotals(list)
                mAdapter.notifyDataSetChanged()
            }

            income_entry.text = Editable.Factory.getInstance().newEditable("")
        } else {
            Toast.makeText(applicationContext, "Entry is Empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentList() : MutableList<Income>?{
        val currentIncomeListString = EzyPrefs.getPreferences(applicationContext, "incomes")
        return currentIncomeListString?.let {
            if (it.isNotEmpty()) {
                EzyUtils.stringToIncomeConversion(
                    it
                ).toMutableList()
            } else {
                mutableListOf()
            }
        }
    }

    private fun computeTotals(list : MutableList<Income>) {
        val total = list.map { it.value }.sum()
        totals.text = getString(R.string.income_ugx, total)
    }

    private fun deleteEntry(income: Income, position: Int) {
        val currentIncomeList = incomeList
        currentIncomeList.remove(income)

        EzyPrefs.setPreferences(applicationContext, EzyUtils.incomeToString(currentIncomeList), "incomes")
        incomeList.clear()
        getCurrentList()?.let {list ->
            incomeList.addAll(list)
            computeTotals(list)
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun editEntry(income: Income, position: Int) {
        income_entry.text = Editable.Factory.getInstance().newEditable(income.value.toString())
        entryEdition = income.timestamp
        add_entry.text = getString(R.string.update)
    }

    private fun edit() {
        val currentIncomeList = incomeList
        currentIncomeList.forEach {
            if (it.timestamp == entryEdition) {
                it.value = income_entry.text.toString().toInt()
            }
        }

        EzyPrefs.setPreferences(applicationContext, EzyUtils.incomeToString(currentIncomeList), "incomes")
        incomeList.clear()

        getCurrentList()?.let {list ->
            incomeList.addAll(list)
            computeTotals(list)
            mAdapter.notifyDataSetChanged()
        }

        add_entry.text = getString(R.string.add_income)
        income_entry.text = Editable.Factory.getInstance().newEditable("")
    }


}