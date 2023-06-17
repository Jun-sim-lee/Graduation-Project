<template>
    <div>
        <div class = "screen">
            <img src="../assets/logo.png" class = "logo">
            <p style = "position: absolute; top: 388px;">인증번호 4자리를 입력해주세요.</p>
            <div class = "otp_wrapper">
                <input class = "otp" style="margin: 3px;" v-model="otpCode1"/>
                <input class = "otp" style="margin: 3px;" v-model="otpCode2"/>
                <input class = "otp" style="margin: 3px;" v-model="otpCode3"/>
                <input class = "otp" style="margin: 3px;" v-model="otpCode4"/>
            </div>
            <button class = "submit_form" style="top: 588px;" @click="sentToServer">확인</button>
        </div>
    </div>
</template>

<script>
export default{
    data(){
        return{
            otpCode1: "",
            otpCode2: "",
            otpCode3: "",
            otpCode4: ""
        }
    },
    methods: {
        sentToServer(){
            alert("서버로 " + this.otpCode1 + this.otpCode2 + this.otpCode3 + this.otpCode4 + " 를 전송합니다.")
            this.$router.replace('main')
        }
    }
}
</script>

<style>
@import "../style/wmistyle.css";
</style>