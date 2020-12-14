package ru.degus.cnjoke.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.degus.cnjoke.R
import ru.degus.cnjoke.components.DiffCallback
import ru.degus.cnjoke.components.replaceQUOT
import ru.degus.cnjoke.databinding.ItemViewBinding
import ru.degus.cnjoke.models.Value

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var items: List<Value> = ArrayList()       //список шуток

    fun setItems(newItems: List<Value>) {   // установка нового списка в Adapter
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, newItems), false)
        items = newItems
        diffResult.dispatchUpdatesTo(this)  //обновления списка с учетом калькуляций DiffUtil

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {  // создание ViewHolder
        val li = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemViewBinding>(
                li,
                R.layout.item_view,
                parent,
                false
        )

        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.itemView.setOnClickListener {                                        //обработка клика на элемент списка
            items[position].expanded = !items[position].expanded                    //изменения флага для расширения
            notifyItemChanged(position)                                             //обновление элемента списка
        }
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

    class MainViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Value) {
            binding.tvJoke.text = item.joke.replaceQUOT()                              //установка текста шутки с заменой кодировки на (")
            binding.tvJoke.isSingleLine = !item.expanded                              //отключение обрезки строки
            binding.tvJoke.maxLines = if (item.expanded) 100 else 1                   //увеличение колличества строк с 1 до 100
        }
    }
}