<script setup>
// import VModal from "@/components/teleport/VModal.vue";
// import {ref} from "vue";

// const showModal = ref(true);
import VModal from "@/components/teleport/VModal.vue";
</script>

<template>
  <section>
    <div
      class="flex flex-col items-center justify-center w-full px-6 py-8 md:h-screen lg:py-0"
    >
      <div class="w-1/3 flex items-center">
        <a href="#" class="flex w-1/3 items-center mx-auto mb-10">
          <img class="w-full" src="../../images/logo-color.svg" alt="logo" />
        </a>
      </div>
      <div class="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
        <div class="p-6 space-y-4 md:space-y-6 sm:p-8">
          <form class="" action="#">
            <div class="mb-4">
              <label
                for="email"
                class="block mb-2 text-sm font-medium text-gray-900"
                >이메일</label
              >
              <input
                type="email"
                name="email"
                id="email"
                class="input input-bordered border-gray-300 w-full text-sm"
                placeholder="user@dktechin.com"
                required
              />
            </div>
            <div>
              <label
                for="password"
                class="block mb-2 text-sm font-medium text-gray-900"
                >비밀번호</label
              >
              <input
                type="password"
                name="password"
                id="password"
                placeholder="비밀번호 입력"
                class="input input-bordered border-gray-300 w-full text-sm"
                required
              />
            </div>
            <div class="flex justify-end">
              <a
                href="#"
                class="mt-2 text-sm font-medium text-indigo-600 hover:underline"
                >비밀번호를 변경하시겠습니까?</a
              >
            </div>
            <!--비밀번호 입력 허용 횟수 5회 초과시 onclick="my_modal_5.showModal()"-->
            <button
              type="submit"
              onclick="my_modal_5.showModal()"
              class="btn btn-primary w-full mt-10"
            >
              로그인
            </button>
            <button
              type="submit"
              class="btn btn-outline btn-primary w-full mt-2"
            >
              회원가입
            </button>
          </form>
        </div>
      </div>
    </div>
  </section>

  <!--로그인 실패-->
  <teleport to="#teleport-area">
    <VModal class="text-center m">
      <template v-slot:header>
        <font-awesome-icon
          icon="fa-regular fa-face-frown"
          style="color: #4f46e5"
          class="block mx-auto mb-3 text-4xl"
        />
        <!--        <font-awesome-icon-->
        <!--          icon="fa-solid fa-recycle"-->
        <!--          style="color: #4f46e5"-->
        <!--          class="block mx-auto mb-2 text-4xl"-->
        <!--        />-->
        <h3 class="mb-7 text-xl font-semibold tracking-tighter text-gray-800">
          로그인 실패
        </h3>
        <!--        <h3 class="mb-7 text-xl font-semibold tracking-tighter text-gray-800">-->
        <!--          비밀번호 변경 권고-->
        <!--        </h3>-->
      </template>
      <template v-slot:body>
        <p class="mb-8 text-base leading-relaxed font-medium text-gray-800">
          이메일(로그인 전용 아이디) 또는 <br />
          비밀번호 입력 허용 횟수를 5회 초과하였습니다.<br />
          해당 계정은 일시적으로 로그인이 차단됩니다.
        </p>
        <!--        <p class="mb-8 text-sm leading-relaxed font-medium text-gray-800">-->
        <!--          개인정보를 안전하게 보호하기 위해<br />-->
        <!--          3개월 이상 비밀번호를 변경하지 않은 경우<br />-->
        <!--          비밀번호 변경을 권장하고 있습니다.-->
        <!--        </p>-->
        <div class="mb-8">
          <span
            class="text-base leading-relaxed font-medium text-gray-600 text-primary"
          >
            남은 시간 :
          </span>
          <span class="countdown font-mono text-xl">
            <span style="--value: 10"></span>분
            <span style="--value: 24"></span>초
          </span>
        </div>
      </template>
      <template v-slot:footer>
        <div class="modal-action">
          <!-- if there is a button in form, it will close the modal -->
          <button class="btn btn-primary w-full mt-5">확인</button>
        </div>
        <!--        <button class="btn btn-primary w-full mt-5">비밀번호 변경하기</button>-->
        <!--        <button class="btn btn-outline btn-primary w-full mt-2">-->
        <!--          다음에 변경하기-->
        <!--        </button>-->
      </template>
    </VModal>
  </teleport>
</template>

<style scoped></style>
