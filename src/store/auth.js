import { createStore } from "vuex"

export default createStore ({
    state : {
        userId: '',
        username: '',
        accessToken: '',
        authority: '',
        isLoggedIn : false
    },
    mutations: {
        login: function(state, payload){
            state.userId = payload.userId
            state.username = payload.username
            state.authority = payload.authority
            state.accessToken = payload.authTokenDTO.accessToken
            state.isLoggedIn = true
        }
    },
    getters: {
        getUserId(state){
            return state.userId
        },
        getUserName(state){ 
            return state.username
        },
        getAccessToken(state){
            return state.accessToken
        },
        getIsLoggedIn(state){
            return state.isLoggedIn
        }
    }
})