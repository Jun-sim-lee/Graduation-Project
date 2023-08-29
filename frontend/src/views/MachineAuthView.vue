<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo"/>
        <h1 style="position: absolute; top: 380px; font-size: 20px">기기
            <span style="color: gray;">(라즈베리파이)</span>
            를 켜주시기 바랍니다.</h1>
        <button class="submit_form" @click="sendRequest" style="top: 488px">완료</button>
    </div>
</template>

<script setup>
import { router } from '@/router'
import axios from 'axios';
import {ref, inject} from 'vue'
import { useStore } from 'vuex';

const store = useStore();
const uniqueRpiCode = ref("")
const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
const requestURL = inject('requestURL')

function sendRequest(){
    axios.post(requestURL + 'frontRpiAuth', uniqueRpiCode.value, {headers})
     .then((resp) => {
        if(resp.data === "Valid"){
            alert(resp.data + " 이므로 입장합니다.")
            router.replace('emailAuth')
        }
        else{
            alert("유효하지 않은 코드입니다!!")
            router.replace('')
        }
     })
     .catch((error) => {
        alert(error)
     })
}
</script>

<style>
@import "../style/wmistyle.css";
</style>