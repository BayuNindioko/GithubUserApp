package com.example.githubuser.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.UserAdapter
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.models.ItemsItem
import com.example.githubuser.viewmodel.FollowViewModel


class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0)

        val sharedPref = requireActivity().getSharedPreferences("NAME", Context.MODE_PRIVATE)
        val username = sharedPref.getString("USERNAME_KEY", "")

        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        binding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())


        if (position == 1) {
            if (username != null) {
                followViewModel.setFollowers(username)
            }
            followViewModel.followersUsers.observe(viewLifecycleOwner, Observer{ followers ->
            if(followers != null){
                setFollowers(followers)
            }
            })
        } else {
            if (username != null) {
                followViewModel.setFollowing(username)
            }
            followViewModel.followingUsers.observe(viewLifecycleOwner, Observer {  following ->
                if(following != null){
                    setFollowing(following)
                }
            })
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowers(followers: List<ItemsItem>) {

        if (followers.isEmpty()) {
            binding.rvFollow.visibility = View.GONE
            binding.placeholderLayout.visibility = View.VISIBLE
        } else {
            binding.rvFollow.visibility = View.VISIBLE
            binding.placeholderLayout.visibility = View.GONE
        }
        val userAdapter=UserAdapter(followers) {

        }
        binding.rvFollow.adapter=userAdapter
    }

    private fun setFollowing(followers: List<ItemsItem>) {

        if (followers.isEmpty()) {
            binding.rvFollow.visibility = View.GONE
            binding.placeholderLayout.visibility = View.VISIBLE
        } else {
            binding.rvFollow.visibility = View.VISIBLE
            binding.placeholderLayout.visibility = View.GONE
        }
        val userAdapter=UserAdapter(followers) {

        }
        binding.rvFollow.adapter=userAdapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollow.visibility = View.VISIBLE
            binding.placeholderLayout.visibility = View.GONE
        } else {
            binding.progressBarFollow.visibility = View.GONE
            binding.placeholderLayout.visibility = View.VISIBLE
        }
    }

    companion object{
        const val ARG_POSITION = "position"
    }
}