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
import OptionView from '../views/OptionView.vue'
import AdminUserView from '../views/AdminUserView.vue'
import AdminAskView from '../views/AdminAskView.vue'
import AdminResourceView from '../views/AdminResourceView'
import DestinationView from '../views/DestinationListView'
import AdminDBView from '../views/AdminDBView'
import AdminMainView from '../views/AdminMainView'
import AdminResourceControlView from '../views/AdminResourceControlView'
import PersonalInformationView from '../views/PersonalInformationView'

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
    { path: '/map', name: 'map', component: MapView },
    { path: '/destination', component: DestinationView },
    { path: '/option', component: OptionView },
    { path: '/adminUser', component: AdminUserView },
    { path: '/adminAsk', component: AdminAskView },
    { path: '/adminResource', component: AdminResourceView },
    { path: '/adminDB', component: AdminDBView },
    { path: '/adminMain', component: AdminMainView },
    { path: '/adminResourceControl', component: AdminResourceControlView },
    { path: '/picontrol', component: PersonalInformationView }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export {router}