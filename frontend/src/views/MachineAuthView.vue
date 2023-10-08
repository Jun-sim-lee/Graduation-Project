<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo"/>
        <img :src="rpiImage" style="position: absolute; top: 310px;"/>
        <h1 style="position: absolute; top: 420px; font-size: 20px">기기
            <span style="color: gray;">(라즈베리파이)</span>
            를 켜주시기 바랍니다.</h1>
        <button class="submit_form" @click="sendRequest" style="top: 500px">완료</button>
    </div>
</template>

<script setup>
import { router } from '@/router'
import axios from 'axios';
import {ref, inject, onMounted, onUnmounted} from 'vue'
import { useStore } from 'vuex';

const store = useStore();
const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
const rpiImage = ref(require("../assets/rpiauth1.drawio.png"))
const imageCount = ref(0)
var timer;

onMounted(() => {
    timer = setInterval(changeImage, 1000);
})

onUnmounted(() => {
    clearInterval(timer);
})

function sendRequest(){
    axios.get(requestURL + 'checkRpiOn', {headers})
     .then((resp) => {
        if(resp.data === true){
            alert("라즈베리파이가 확인되었습니다.\n입장합니다.")

            if(store.state.authority === "None")
                router.replace('emailAuth')
            else
                router.replace('main')
        }
        else{
            alert("유효하지 않은 고유 코드입니다!!\n메인화면으로 이동합니다.")
            localStorage.clear()
            router.replace('/')
        }
     })
     .catch((error) => {
        alert(error)
     })
}

function changeImage(){
    imageCount.value++
    rpiImage.value = require("../assets/rpiauth" + ((imageCount.value)%4 + 1) + ".drawio.png")   
}
</script>

<style>
@import "../style/wmistyle.css";
</style>