import {createRouter, createWebHistory} from 'vue-router'
import InitPage from '../views/InitPage.vue'
import LoginPage from '../views/LoginPage.vue'
import SignUpPage from '../views/SignUpPage.vue'


const routes = [
    { path: '/', component: InitPage },
    { path: '/login', component: LoginPage },
    { path: '/signup', component: SignUpPage}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export {router}