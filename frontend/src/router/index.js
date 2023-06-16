import {createRouter, createWebHistory} from 'vue-router'
import InitView from '../views/InitView.vue'
import LoginView from '../views/LoginView.vue'
import SignUpView from '../views/SignUpView.vue'
import EmailAuthView from '../views/EmailAuthView.vue'
import MainView from '../views/MainView.vue'
import BindTest from '../views/BindTest.vue'
import OtpAuthView from '../views/OtpAuthView.vue'
import QRAuthView from '../views/QRAuthView.vue'

const routes = [
    { path: '/', component: InitView },
    { path: '/login', component: LoginView },
    { path: '/signup', component: SignUpView },
    { path: '/emailAuth', component: EmailAuthView },
    { path: '/main', component: MainView },
    { path: '/test', component: BindTest },
    { path: '/qr', component: QRAuthView },
    { path: '/otp', component: OtpAuthView }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export {router}