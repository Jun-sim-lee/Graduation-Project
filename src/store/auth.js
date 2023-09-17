import { createStore } from "vuex"
import createPersistedState from 'vuex-persistedstate'

export default createStore ({
    state : {
        username: '',
        accessToken: '',
        authority: '',
        isLoggedIn : false
    },
    mutations: {
        login: function(state, payload){
            state.username = payload.username
            state.authority = payload.authority
            state.accessToken = payload.authTokenDTO.accessToken // 이부분도 고쳐야함 다시!!
            state.isLoggedIn = true
        }
    },
    getters: {
        getUserName(state){ 
            return state.username
        },
        getAccessToken(state){
            return state.accessToken
        },
        getIsLoggedIn(state){
            return state.isLoggedIn
        }
    },
    plugins: [
        createPersistedState({})
        // 원하는 페이지에서만 저장하도록 수정해볼 것
    ]
})

