package com.example.movierecommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movierecommendation.api.TmdbRequest
import com.example.movierecommendation.dataclass.Country
import com.example.movierecommendation.dataclass.Item


class MovieViewModel : ViewModel() {
    private val tmdbRequest = TmdbRequest()

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _trending = MutableLiveData<List<Item>>()
    val trendingList: LiveData<List<Item>> = _trending

    private val _topMovies = MutableLiveData<List<Item>>()
    val topMovies: LiveData<List<Item>> = _topMovies

    private val _newMovies = MutableLiveData<List<Item>>()
    val newMovies: LiveData<List<Item>> = _newMovies

    private val _upcomingMovies = MutableLiveData<List<Item>>()
    val upcomingMovies: LiveData<List<Item>> = _upcomingMovies

    private val _topTv = MutableLiveData<List<Item>>()
    val topTv: LiveData<List<Item>> = _topTv

    private val _newTv = MutableLiveData<List<Item>>()
    val newTv: LiveData<List<Item>> = _newTv

    private val _upcomingTv = MutableLiveData<List<Item>>()
    val upcomingTv: LiveData<List<Item>> = _upcomingTv

    private val _selectedRegionText = MutableLiveData("Select A Region")
    val selectedRegionText: LiveData<String> = _selectedRegionText

    private val _selectedRegionIsoCode = MutableLiveData("")
    private val selectedRegionIsoCode: LiveData<String> = _selectedRegionIsoCode

    private val _autoDetectedCountryIso = MutableLiveData("Null")
    val autoDetectedCountryIso: LiveData<String> = _autoDetectedCountryIso


    private val _scrollOffset = MutableLiveData(0)
    val scrollOffset: LiveData<Int> = _scrollOffset

    private val _scrollIndex = MutableLiveData(0)
    val scrollIndex: LiveData<Int> = _scrollIndex


    fun updateScrollOffset(offset: Int) {
        _scrollOffset.value = offset
    }

    fun updateScrollIndex(index: Int) {
        _scrollIndex.value = index
    }

    fun setSelectedRegion(isoCode: String, englishName: String) {
        Log.d("test", "call success $isoCode $englishName")
        _selectedRegionIsoCode.value = isoCode
        _selectedRegionText.value = englishName
        fetchRegionSpecificMovies(selectedRegionIsoCode)

    }

    fun setAutoDetectedCountryIso(autoDetectedCountryIso: String?) {
        _autoDetectedCountryIso.value = autoDetectedCountryIso

    }

    private fun fetchTrendingMovies() {
        tmdbRequest.fetchTrending(
            success = { items ->
                _trending.postValue(items)
            },
            failure = { error ->
                Log.e("TrendingMovies", "Failed to fetch trending movies: ", error)
            }
        )
    }


    private fun fetchCountries() {
        tmdbRequest.fetchCountries(
            success = { countries ->
                _countries.postValue(countries)
            },
            failure = { error ->
                Log.e("Countries", "Failed to fetch Countries: ", error)
            }
        )
    }

    private fun fetchTopMovies(countryIsoCode: String? = null) {
        tmdbRequest.fetchTopMovies(countryIsoCode,
            success = { items ->
                _topMovies.postValue(items)
            },
            failure = { error ->
                Log.e("TopMovies", "Failed to fetch top movies: ", error)
            }

        )
    }

    private fun fetchNewMovies(countryIsoCode: String? = null) {
        tmdbRequest.fetchNewMovies(countryIsoCode,
            success = { items ->
                _newMovies.postValue(items)
            },
            failure = { error ->
                Log.e("NewMovies", "Failed to fetch New movies: ", error)
            }
        )

    }

    private fun fetchUpcomingMovies(countryIsoCode: String? = null) {
        tmdbRequest.fetchUpcomingMovies(countryIsoCode,
            success = { items ->
                _upcomingMovies.postValue(items)
            },
            failure = { error ->
                Log.e("UpcomingMovies", "Failed to fetch upcoming movies: ", error)
            }
        )
    }

    private fun fetchTopTv() {
        tmdbRequest.fetchTopTv(
            success = { items ->
                _topTv.postValue(items)
            },
            failure = { error ->
                Log.e("TopTv", "Failed to fetch Top Tv: ", error)
            }
        )

    }

    private fun fetchNewTv(timeZoneIsoCode: String? = null) {
        tmdbRequest.fetchNewTv(timeZoneIsoCode,
            success = { items ->
                _newTv.postValue(items)
            },
            failure = { error ->
                Log.e("NewTv", "Failed to fetch new Tv: ", error)
            }
        )
    }

    private fun fetchUpcomingTv(timeZoneIsoCode: String? = null) {
        tmdbRequest.fetchUpcomingTv(timeZoneIsoCode,
            success = { items ->
                _upcomingTv.postValue(items)
            },
            failure = { error ->
                Log.e("UpcomingTv", "Failed to fetch upcoming Tv: ", error)
            }
        )
    }

    fun fetchConfig() {
        fetchCountries()
    }

    fun fetchData() {
        fetchConfig()
        fetchTrendingMovies()
        fetchTopMovies()
        fetchNewMovies()
        fetchUpcomingMovies()
        fetchTopTv()
        fetchNewTv()
        fetchUpcomingTv()
    }

    private fun fetchRegionSpecificMovies(isoCode: LiveData<String>) {

        if (isoCode.value == "ALL") {
            fetchTopMovies()
            fetchNewMovies()
            fetchUpcomingMovies()
            fetchNewTv()
            fetchUpcomingTv()

        } else {
            fetchTopMovies(isoCode.toString())
            fetchNewMovies(isoCode.toString())
            fetchUpcomingMovies(isoCode.toString())
            fetchNewTv(isoCode.toString())
            fetchUpcomingTv(isoCode.toString())
        }
    }
}