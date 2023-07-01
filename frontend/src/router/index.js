import {createRouter, createWebHistory} from 'vue-router'
import InitView from '../views/InitView.vue'
import LoginView from '../views/LoginView.vue'
import SignUpView from '../views/SignUpView.vue'
import EmailAuthView from '../views/EmailAuthView.vue'
import MainView from '../views/MainView.vue'
import OtpAuthView from '../views/OtpAuthView.vue'
import QRAuthView from '../views/QRAuthView.vue'
import MachineAuthView from '../views/MachineAuthView.vue'
import AllResourceView from '../views/AllResourceView.vue'
import MyResourceView from '../views/MyResourceView.vue'
import MapView from '../views/MapView.vue'

const routes = [
    { path: '/', component: InitView },
    { path: '/login', component: LoginView },
    { path: '/signup', component: SignUpView },
    { path: '/emailAuth', component: EmailAuthView },
    { path: '/main', component: MainView },
    { path: '/qr', component: QRAuthView },
    { path: '/otp', component: OtpAuthView },
    { path: '/machineAuth', component: MachineAuthView },
    { path: '/allResource', component: AllResourceView },
    { path: '/myResource', component: MyResourceView },
    { path: '/map', component: MapView }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export {router}