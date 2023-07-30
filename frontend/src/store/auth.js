import { createStore } from "vuex"

export default createStore ({
    state : {
        userId: 'dndlzm123',
        userName: '김금례',
        accessToken: 'helloitsaccesstoken',
        isLoggedIn : false
    },
    mutations: {
        login: function(state, payload){
            state.userId = payload.userId
            state.userName = payload.userName
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