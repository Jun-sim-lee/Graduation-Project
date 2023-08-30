<template>
    <div class = "screen">
        <div class = "resource_header">
            <div class = "inner_header">
                <button @click="moveToPrev" style="line-height: 50px; color: white; background-color: transparent; border: none; font-size: 20px"> &lt; 나의 리소스</button>
            </div>
            <p style = "position: absolute; color: white; left: 27px; top: 40px; font-size: 30px; font-weight: bold; font-family: serif;">My Resource</p>
            <p style = "position: absolute; color: white; left: 27px; top: 100px;">직접 추가한 리소스 목록입니다.<br>
                근처에 도달하면 자원을 자유롭게 제어할 수 있습니다.</p>
        </div>
        <div style="width: 389px; height: 611px; position: relative; background-color: white;">
            <ul>
                <li class = "resource_list" style="display: flex; flex-direction: row; justify-content: space-between;"
                    :key="resource.name" v-for="resource in resourceList">
                    <img :src="resource.imgsrc" style="width: 25px; height: 25px;">
                    <span style="font-size: 18px; line-height: 30px;">{{ resource.name }}</span>
                    <button @click="requestDeletion" style="border: none; border-radius: 30px; background-color: red; width: 50px; height: 30px;
                                   font-size: 10px; color: white; ">삭제</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import {ref, onMounted, inject} from 'vue';
import { router } from '@/router';
import { useStore } from 'vuex';
import axios from 'axios';

const store = useStore();
const headers = JSON.parse(inject('headers') + store.state.accessToken + '"}');
const requestURL = inject('requestURL')

const resourceList = ref([
    {imgsrc: require("../assets/vendor_machine.png"), name: "자판기"},
    {imgsrc: require("../assets/door_lock.png"), name: "도어락"},
    {imgsrc: require("../assets/lamp.png"), name: "전등"},
    {imgsrc: require("../assets/monitor.jpg"), name: "모니터"}
])

onMounted(() => { // 화면 마운트 시 요청 받아옴
    requestResourceList();
})

function requestResourceList(){
    axios.get(requestURL + "resource", {headers})
        .then((resp) => {
            resourceList.value = resp.data;
        })
}

function requestDeletion(){
    // 삭제 요청
}

function moveToPrev(){
    router.go(-1)
}
</script>

<style>
@import "../style/wmistyle.css";
</style>