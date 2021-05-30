package ru.startandroid.develop.ivelost.view.fragments.messages

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.startandroid.develop.ivelost.R
import ru.startandroid.develop.ivelost.databinding.FragmentChatsBinding
import ru.startandroid.develop.ivelost.module.data.MessageItem
import ru.startandroid.develop.ivelost.view.recyclerView.ChatsFragmentAdapter
import ru.startandroid.develop.testprojectnavigation.utils.hideDrawer
import ru.startandroid.develop.testprojectnavigation.utils.lockDrawer
import ru.startandroid.develop.testprojectnavigation.utils.stringGet

class FragmentChats : Fragment(R.layout.fragment_chats), ChatsFragmentAdapter.OnItemClickListener {
    //? binding; apply; bottomNavigation; fab clickListener, все это законментировано в FragmentProfile.kt
    private lateinit var binding: FragmentChatsBinding
    private var dummyData = ArrayList<MessageItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = LinearLayoutManager(activity)
        dummyData = generateItemList(5)
        binding = FragmentChatsBinding.bind(view)

        binding.recyclerMessagesView.adapter = ChatsFragmentAdapter(dummyData, this)
        binding.apply {
            bottomNavMessages.setupWithNavController(findNavController())

            fabChats.setOnClickListener {
                val action = FragmentChatsDirections.actionGlobalFragmentAddLostFind2()
                findNavController().navigate(action)
            }

            recyclerMessagesView.layoutManager = manager
            recyclerMessagesView.setHasFixedSize(true)
            recyclerMessagesView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    //? убираем иконку бургер и также блокируем выдвижение drawerLayout
    override fun onStart() {
        super.onStart()
        lockDrawer()
        hideDrawer()
    }

    private fun generateItemList(size: Int): ArrayList<MessageItem> {
        // the we create new empty arrayList<>
        val list = ArrayList<MessageItem>()

        // and it uses the size value in the for loop to fill this list with data
        // Note: this is a custom algorithm that has nothing to do neither with android nor recyclerView
        for (i in 0 until size) {
            // this part is only responsible for alternating between our 5 drawables
            val userImage = when (i % 5) {
                0 -> R.drawable.av_one
                1 -> R.drawable.av_two
                2 -> R.drawable.av_new_four
                3 -> R.drawable.av_four
                else -> R.drawable.ninja
            }

            val userName = when (i % 5) {
                0 -> stringGet(R.string.messages_names_one)
                1 -> stringGet(R.string.messages_names_two)
                2 -> stringGet(R.string.messages_names_three)
                3 -> stringGet(R.string.messages_names_four)
                else -> stringGet(R.string.messages_names_five)
            }

            val lastMessage = when (i % 5) {
                0 -> stringGet(R.string.messages_messages_one)
                1 -> stringGet(R.string.messages_messages_two)
                2 -> stringGet(R.string.messages_messages_three)
                3 -> stringGet(R.string.messages_messages_four)
                else -> stringGet(R.string.messages_messages_five)
            }

            // creates new ExampleItem and passes through its constructor the necessary data
            val item = MessageItem(userImage, userName, lastMessage, 0)
            list += item
        }
        // after filling the list with data we eventually return it
        return list
    }

    override fun onItemClick(position: Int) {
        val clickedUserName = dummyData[position].userName
        val clickedUserPhoto = dummyData[position].userImage

        val action = FragmentChatsDirections.actionFragmentMessages2ToFragmentChats(clickedUserName, clickedUserPhoto)
        findNavController().navigate(action)
    }
}