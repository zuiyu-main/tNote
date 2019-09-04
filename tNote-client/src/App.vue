<template>
  <!-- 可以这样理解，正常写法中，一层路径(/xxx)对应一个router-view。

比如url: /a/b/c (假设a、b、c都为正常路径，不会作为参数)

那/a对应的就是App.vue中的router-view，/a进入a.vue中
那/a/b对应的就是a.vue中的router-view， /a/b进入b.vue中
  以此类推。-->
  <router-view />
</template>
