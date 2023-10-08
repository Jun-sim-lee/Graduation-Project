<template>
    <div class = "screen">
        <div style="width: 389px; height: 65px; background-color: #537FE7;">
            <p style="color: white; padding-left: 20px; font-size: 20px"> 관리자 페이지 </p>
        </div>
        <div style="width: 389px; height: 660px; background-color: white; position: relative">
            <div style=" width: 330px; height: 40px; border-bottom: 2px solid black;
                         position: absolute; top: 20px; left: 20px;">
                <p class = "admin_button">신규 리소스 등록</p>
                <input type="text" placeholder="리소스 이름" v-model="resourceDto.deviceName"><br>
                <select v-model="resourceDto.auth" style="width: 147px; height: 21.5px;">
                    <option value="" selected disabled hidden>사용 가능 권한</option>
                    <option value="일반">일반</option>
                    <option value="학생">학생</option>
                    <option value="교수">교수</option>
                </select><br>
                <input type="text" placeholder="x 좌표" v-model="resourceDto.x" style="width: 65px;">
                <input type="text" placeholder="y 좌표" v-model="resourceDto.y" style="width: 65px;"> <button @click="registerResource">등록</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import {ref, inject} from 'vue'
import axios from 'axios'

const accessToken = localStorage.getItem('accessToken')
const headers = JSON.parse(inject('headers') + accessToken + '"}');
const requestURL = inject('requestURL')
const resourceDto = ref({
    deviceName: "",
    auth: "",
    x: "",
    y: ""
})

function registerResource(){
    axios.post(requestURL + "admin/registerResource", resourceDto.value, {headers})
        .then((resp) => {
            if(resp.status === 200)
                alert("등록이 완료되었습니다.")
        })
}

</script>

<style>
@import "../style/wmistyle.css";
</style>