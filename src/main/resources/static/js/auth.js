let index = {
  init: function () {
    localStorage.setItem("loginFlg", null);
    $("#btn-join").on("click", () => { // ()=> 내부의 함수를 this를 바인딩하기 위해
      this.save();
    });

    $("#btn-login").on("click", () => { // ()=> 내부의 함수를 this를 바인딩하기 위해
      this.login();
    });
  },
  save: function () {
    let data = {
      username: $("#username").val(),
      password: $("#password").val(),
      email: $("#email").val()
    }

    commonMethod.ajaxTransmit("POST", "/auth/join", data, this.saveCallback, this.errCallback);
  },

  login: function () {
    let data = {
      email: $("#email").val(),
      password: $("#password").val()
    }

    commonMethod.ajaxTransmit("POST", "/auth/login", data, this.loginCallback, this.errCallback);
  },

  loginCallback: function (xhr) {
    alert('로그인 완료');
    //      localStorage.setItem("access", xhr.data.grantType + xhr.data.accessToken);
    localStorage.setItem("loginFlg", 1);
    $("#nonlogin").css('display', 'none');
    location.href = "/";
  },

  saveCallback: function () {
    alert('회원가입이 완료');
    location.href = "/";
  },

  errCallback: function (xhr) {
    data = xhr.responseJSON;
    commonMethod.setErr(data);
    commonMethod.setFieldErr(data.errors);
  }
}

index.init();