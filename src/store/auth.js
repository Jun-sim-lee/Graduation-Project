import { createStore } from "vuex"

export default createStore ({
    state : {
        userId: '',
        userName: '',
        accessToken: '',
        authority: '',
        isLoggedIn : false
    },
    actions: {
        login: function(state, payload){
            state.userId = payload.userId
            state.userName = payload.userName
            state.authority = payload.authority
            state.accessToken = payload.accessToken
            state.isLoggedIn = true
        }
    },
    getters: {
        getUserId(state){
            return state.userId
        },
        getUserName(state){ 
            return state.userName
        },
        getAccessToken(state){
            return state.accessToken
        },
        getIsLoggedIn(state){
            return state.isLoggedIn
        }
    }
})