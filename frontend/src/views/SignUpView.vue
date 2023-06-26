<template>
    <div class = "screen">
        <img src="../assets/logo.png" class = "logo">
        <input class = "wmi_input" v-model = "signUpDto.userId" style="top: 377px; padding-left: 3%;" placeholder="아이디"/>
        <input type="password" class = "wmi_input" v-model = "signUpDto.password" style="top: 433px; padding-left: 3%;" placeholder="비밀번호"/>
        <input type="password" v-model = "checkedPw" style="top: 488px; padding-left: 3%;" placeholder="비밀번호 확인"
               v-bind:class="{'wmi_input': valid, 'input_error': invalid}"/>
        <p v-if="valid === false" style="position: absolute; top: 525px; font-size: 12px; color: red;">비밀번호가 다릅니다.</p>
        <button class = "submit_form" @click = "sendRequest" style="top: 577px;">회원가입</button>
    </div>
</template>

<script setup>
import { router } from '@/router';
import axios from 'axios'
import {ref} from 'vue'

const valid = ref(true)
const invalid = ref(false)
const checkedPw = ref()
const signUpDto = ref({
    userId: "",
    password: ""
})

function checkPassword(){
    if (signUpDto.value.password === checkedPw.value){
        valid.value = true
        invalid.value = false
    }
    else{
        valid.value = false
        invalid.value = true
    }
}

function sendRequest(){
    checkPassword()
    if(valid.value === true){
        axios.post("http://localhost:8080/signup", signUpDto.value)
            .then((resp) => {
                if(resp.data === "OK")
                    router.replace('emailAuth')
                else
                    alert("회원가입에 실패하였습니다..")
            })
            .catch((error) => {
                alert(error)
            })
    }
}
</script>

<style>
@import "../style/wmistyle.css";
</style>