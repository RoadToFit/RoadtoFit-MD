package com.capstone.sofitapp.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.roadtofit.data.model.UserPreference
import com.example.roadtofit.data.response.DietResponse
import com.example.roadtofit.data.response.FoodResponse
import kotlinx.coroutines.launch

class DietViewModel(  private val repo: UserPreference) : ViewModel() {

    val dietResponse: LiveData<DietResponse> = repo.dietResponse
    val foodResponse: LiveData<FoodResponse> = repo.foodResponse



    fun doPredict(input: String) {
        viewModelScope.launch {
            repo.postPredict(input)
        }
    }

    fun doFood(gender: String, height:Int, weight: Int, age: Int, activity: String, plan: String,numMeals: Int, bodyType: String) {
        viewModelScope.launch {
            repo.postFood(gender,height,weight, age, activity, plan, numMeals, bodyType)
        }
    }


}