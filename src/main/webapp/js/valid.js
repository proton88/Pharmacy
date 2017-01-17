function validateRegisterForm() {
	var result = true; // результат, используем, чтобы проверять сразу все поля ввода

// Находим ссылки на элементы сообщений об ошибках
	var errUnameField = document.getElementById("err_uname_field"),
		errUnameLogin = document.getElementById("err_uname_login"),
		errPswEmpty =  document.getElementById("err_psw_empty"),
		errPswBad =  document.getElementById("err_psw_bad"),
		errPswNotEquals =  document.getElementById("err_psw_not_equals"),
		errPsw2Empty =  document.getElementById("err_psw2_empty"),
		errPsw2NotEquals =  document.getElementById("err_psw2_not_equals"),
		errMailEmpty =  document.getElementById("err_mail_empty"),
		errMailBad =  document.getElementById("err_mail_bad"),
		errNameEmpty =  document.getElementById("err_name_empty"),
		errSurnameEmpty =  document.getElementById("err_surname_empty"),
		errAdressEmpty =  document.getElementById("err_adress_empty"),
		errPassportEmpty =  document.getElementById("err_passport_empty"),
		errPassportBad =  document.getElementById("err_passport_bad");

// Предварительно очищаем сообщения об ошибках
	errUnameField.style.display="none";
	errUnameLogin.style.display="none";
	errPswEmpty.style.display="none";
	errPswBad.style.display="none";
	errPswNotEquals.style.display="none";
	errPsw2Empty.style.display="none";
	errPsw2NotEquals.style.display="none";
	errMailEmpty.style.display="none";
	errMailBad.style.display="none";
	errNameEmpty.style.display="none";
	errSurnameEmpty.style.display="none";
	errAdressEmpty.style.display="none";
	errPassportEmpty.style.display="none";
	errPassportBad.style.display="none";
// Читаем значения из полей формы
	var usr = document.registerForm.login_reg.value,
		pwd1 = document.registerForm.password_reg.value,
		pwd2 = document.registerForm.passwordRepeat.value,
		mail = document.registerForm.email.value,
		name = document.registerForm.name.value,
		surname = document.registerForm.surname.value,
		adress = document.registerForm.adress.value,
		passportId = document.registerForm.passportId.value;

// Проверка поля "Пользователь"
	if (!usr) {
		errUnameField.style.display="inline";
		result = false;
	}  // обязательное заполнение
	if (usr && !/^[a-zA-Z][a-zA-Z0-9_]{4,}$/.test(usr)) {
		errUnameLogin.style.display="inline";
		document.forms["registerForm"]["login_reg"].value = "";   // сброс
		result = false;
	}  // имя пользователя (латинские буквы, цифры, _, первый символ – латинская буква), кол-во символов не менее 5.
// Проверка паролей
	if (!pwd1) {
		errPswEmpty.style.display="inline";
		result = false;
	}  // обязательное заполнение
	if (!pwd2) {
		errPsw2Empty.style.display="inline";
		result = false;
	}  // обязательное заполнение
	if (pwd1 && pwd2 && pwd1 !== pwd2) {
		errPswNotEquals.style.display="inline";
		errPsw2NotEquals.style.display="inline";
		document.forms["registerForm"]["password_reg"].value = "";   // сброс
		document.forms["registerForm"]["passwordRepeat"].value = "";   // сброс
		result = false;
	}   // должны совпадать
	if (pwd1 && pwd2 && !/(?=^.{6,}$)^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/.test(pwd1)){
		errPswBad.style.display="inline";
		document.forms["registerForm"]["password_reg"].value = "";   // сброс
		document.forms["registerForm"]["passwordRepeat"].value = "";   // сброс
		result = false;
	}//не менее 6 символов, не менее одной буквы в каждом регистре и не менее одной цифры

// Проверка поля "Name"
	if (!name) {
		errNameEmpty.style.display = "inline";
		result = false;
	}

// Проверка поля "Surname"
	if (!surname) {
		errSurnameEmpty.style.display = "inline";
		result = false;
	}

// Проверка поля "Adress"
	if (!adress) {
		errAdressEmpty.style.display = "inline";
		result = false;
	}

// Проверка поля "Passport"
	if (!passportId) {
		errPassportEmpty.style.display = "inline";
		result = false;
	}
	if (passportId && !/[A-Z]{2}[0-9]{7}/.test(passportId)){
		errPassportBad.style.display="inline";
		document.forms["registerForm"]["passportId"].value = "";   // сброс
		result = false;
	}

//Проверка почты
	if (!mail) {
		errMailEmpty.style.display="inline";
		result = false;
	}  // обязательное заполнение
	if (mail && !/^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$/.test(mail)){
		errMailBad.style.display="inline";
		document.forms["registerForm"]["email"].value = "";   // сброс
		result = false;
	}
// возвращаем итог проверки
	return result;
}

function validateExtendRecipeForm(){
	var result = true;

	var errPeriodField = document.getElementById("err_period_field"),
		errPeriodBad = document.getElementById("err_period_bad");

	errPeriodField.style.display="none";
	errPeriodBad.style.display="none";

	var period = document.extendRecipeForm.period.value;


	if (!period) {
		errPeriodField.style.display="inline";
		result = false;
	}
	if (period && ((/[^[0-9]/.test(period)) || period < 1 || period > 60)) {
		errPeriodBad.style.display="inline";
		document.forms["extendRecipeForm"]["period"].value = "";   // сброс
		result = false;
	}

	return result;
}

function validateCancelRecipeForm(){
	var result = true;

	var errFields = document.getElementById("err_fields"),
		errClientId = document.getElementById("err_format_id_client");

	errFields.style.display="none";
	errClientId.style.display="none";

	var drugName = document.cancelRecipeForm.drugName.value,
		clientId = document.cancelRecipeForm.clientId.value;


	if (!drugName || !clientId) {
		errFields.style.display="inline";
		result = false;
	}
	if (clientId && (/[^[0-9]/.test(clientId))) {
		errClientId.style.display="inline";
		document.forms["cancelRecipeForm"]["clientId"].value = "";   // сброс
		result = false;
	}

	return result;
}

function validateAssignRecipeForm(){
	var result = true;

	var errFields = document.getElementById("err_fields"),
		errClientId = document.getElementById("err_format_id_client"),
		errDrugId = document.getElementById("err_format_id_drug"),
		errQuantity = document.getElementById("err_quantity_bad"),
		errPeriod = document.getElementById("err_period_bad"),
		errCode = document.getElementById("err_code_bad");

	errFields.style.display="none";
	errClientId.style.display="none";
	errDrugId.style.display="none";
	errQuantity.style.display="none";
	errPeriod.style.display="none";
	errCode.style.display="none";


	var period = document.assignRecipeForm.period.value,
		clientId = document.assignRecipeForm.clientId.value,
		drugId = document.assignRecipeForm.drugId.value,
		quantity = document.assignRecipeForm.quantity.value,
		code = document.assignRecipeForm.code.value;


	if (!period || !clientId || !drugId || !quantity || !code) {
		errFields.style.display="inline";
		result = false;
	}
	if (drugId && (/[^[0-9]/.test(drugId))) {
		errDrugId.style.display="inline";
		document.forms["assignRecipeForm"]["drugId"].value = "";   // сброс
		result = false;
	}
	if (clientId && (/[^[0-9]/.test(clientId))) {
		errClientId.style.display="inline";
		document.forms["assignRecipeForm"]["clientId"].value = "";   // сброс
		result = false;
	}
	if (quantity && (/[^[0-9]/.test(quantity) || quantity<1 || quantity>9)) {
		errQuantity.style.display="inline";
		document.forms["assignRecipeForm"]["quantity"].value = "";   // сброс
		result = false;
	}
	if (period && ((/[^[0-9]/.test(period)) || period < 1 || period > 60)) {
		errPeriod.style.display="inline";
		document.forms["assignRecipeForm"]["period"].value = "";   // сброс
		result = false;
	}

	return result;
}

function validateLoginForm() {
	var result = true;

	var errFields = document.getElementById("err_fields"),
		errUnameLogin = document.getElementById("err_login"),
		errPswBad =  document.getElementById("err_password");

	errFields.style.display="none";
	errUnameLogin.style.display="none";
	errPswBad.style.display="none";

	var usr = document.loginForm.login.value,
		pwd1 = document.loginForm.password.value;

	if (!usr || !pwd1) {
		errFields.style.display="inline";
		result = false;
	}
	if (usr && !/^[a-zA-Z][a-zA-Z0-9_]{4,}$/.test(usr)) {
		errUnameLogin.style.display="inline";
		document.forms["loginForm"]["login"].value = "";
		result = false;
	}

	if (pwd1 && !/(?=^.{6,}$)^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\s).*$/.test(pwd1)){
		errPswBad.style.display="inline";
		document.forms["loginForm"]["password"].value = "";
		result = false;
	}

	return result;
}

function validateAddDrugForm(){
	var result = true;

	var errFields = document.getElementById("err_fields"),
		errCountry = document.getElementById("err_bad_country"),
		errPrice = document.getElementById("err_bad_price"),
		errQuantity = document.getElementById("err_bad_quantity");

	errFields.style.display="none";
	errCountry.style.display="none";
	errPrice.style.display="none";
	errQuantity.style.display="none";

	var drugName = document.addDrugForm.drugName.value,
		drugCategories = document.addDrugForm.drugCategories.value,
	 	country = document.addDrugForm.country.value,
		priceDrug = document.addDrugForm.priceDrug.value,
		quantity = document.addDrugForm.quantity.value;



	if (!drugName || !country || !priceDrug || !quantity || !drugCategories) {
		errFields.style.display="inline";
		result = false;
	}
	if (country && (/[^а-яА-Я-\s]/.test(country))) {
		errCountry.style.display="inline";
		document.forms["addDrugForm"]["country"].value = "";   // сброс
		result = false;
	}
	if (priceDrug && !(/\b\d+.\d{2}\b/.test(priceDrug))) {
		errPrice.style.display="inline";
		document.forms["addDrugForm"]["priceDrug"].value = "";   // сброс
		result = false;
	}
	if (quantity && (/[^[0-9]/.test(quantity))) {
		errQuantity.style.display="inline";
		document.forms["addDrugForm"]["quantity"].value = "";   // сброс
		result = false;
	}

	return result;
}