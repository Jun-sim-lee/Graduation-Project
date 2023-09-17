<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 개인 정보 변경 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white; position: relative">
            <div style=" width: 330px; height: 40px; border-bottom: 2px solid black;
                         position: absolute; top: 20px; left: 20px;">
                <p class = "admin_button">개인 정보 변경</p>
                <input type="text" v-model="userInfoDto.nickName"><br>
                <input type="password" placeholder="비밀번호" v-model="userInfoDto.password"> <button @click="changePassword">변경</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios';
import { ref, inject } from 'vue';
import { useStore } from 'vuex';
import {router} from '@/router'

const store = useStore();
const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
const userInfoDto = ref({
    nickName: store.state.username,
    password: ""
})

function changePassword(){
    axios.post(requestURL + "changeInfo", userInfoDto.value, {headers})
        .then((resp) => {
            if(resp.status === 200){
                alert("개인 정보 변경이 완료되었습니다.")
                router.go(-1)
            }
        })
}

</script>

<style>
@import "../style/wmistyle.css";
</style>