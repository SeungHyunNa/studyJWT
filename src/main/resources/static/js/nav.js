var loginFlg = localStorage.loginFlg;
if (loginFlg == 1) {
  var ul = $("<ul id='123' class='navbar-nav me-auto mb-2 mb-md-0'>");
  var board = $("<li class='nav-item'> <a class='nav-link' href='/board/form'>게시판</a> </li>");
  var boardSaveForm = $("<li class='nav-item'> <a class='nav-link' href='/board/saveForm'>글쓰기</a> </li>");
  var userInfo = $("<li class='nav-item'> <a class='nav-link' href='/user/form'>회원정보</a> </li>");
  var logout = $("<li class='nav-item'> <a class='nav-link' id='logout' href='#'>로그아웃</a> </li>");
  ul = ul.append(board).append(boardSaveForm).append(userInfo).append(logout);
  $("#navbarCollapse").prepend(ul);
} else {
  var ul = $("<ul id='123' class='navbar-nav me-auto mb-2 mb-md-0'>");
  var login = $("<li class='nav-item'> <a class='nav-link' href='/auth/loginForm'>Login</a> </li>");
  var joinForm = $("<li class='nav-item'> <a class='nav-link' href='/auth/joinForm'>JOIN</a> </li>");
  ul = ul.append(login).append(joinForm);
  $("#navbarCollapse").prepend(ul);

}

let doLogout = {

  init: function () {
    $("#logout").on("click", () => { // ()=> 내부의 함수를 this를 바인딩하기 위해
      this.logout();
    });
  },

  logout: function () {
    commonMethod.ajaxTransmit("POST", "/auth/logout", null, this.logoutCallback, null);
  },

  logoutCallback: function () {
    localStorage.setItem("loginFlg", null);
    location.href = "/";
  }
}

doLogout.init();