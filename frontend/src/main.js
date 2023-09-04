import {createApp} from 'vue'
import App from './App.vue'
import {router} from './router/index.js'
import store from './store/auth.js'
import './registerServiceWorker.js'

const app = createApp(App)

app.use(router)
app.use(store)

const headers = '{"Content-type":"application/json; charset=UTF-8", "Authorization": "Bearer '
app.provide('headers', headers)
app.provide('requestURL', "http://13.211.135.69:8080/")

app.mount('#app')