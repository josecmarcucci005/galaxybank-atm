new Vue({
    el: '#app',
    data: {
        welcomePanelShow: true,
        menuPanelShow: false,
        operationPanelShow: false,
        operationType: '',
        showAccountBalance: false,
        showOperationResult: false,
        displayPanelShow: false,
        email: '',
        password: '',

        type: null,
        elapse: null,
        errorMsg: null,

        atm: null,
        user: null,
        amount: ''

    },

    methods: {
      changeSelectedFieldInput: function (number) {
          this.amount += number
      },

      resetSelectedFieldInput: function () {
          this.amount = ''
      },

      validateUser: function() {
         if (this.email == '' || this.password == '') {
            this.showAlert('warning', 'You must enter your email and pasword!')
         } else {
            var params = {
            email: this.email,
            pwd: this.password
          }

          var url = 'https://atm-galaxybank.herokuapp.com/galaxybank/loginAdmin'

          this.makeAxiosCall(url, params, function(temp, response) {
            if (response.data.result == 'ERROR') {
              temp.showAlert('warning','There was an error with the login! ErrorMsg:' + response.data.errMsg)
            } else {
              temp.user = response.data
              temp.menuPanelShow = true
              temp.welcomePanelShow = false

              temp.getATMInformation()
            }
          })
         }
      },

      addAmountToATMBalance: function() {
         if (this.amount == '' || this.amount == '0') {
            this.showAlert('warning', 'You must enter an amount value!')
         } else {
            var amnt = parseFloat(this.atm.data.balance) + parseFloat(this.amount)
            var params = {
              atmId: 1,
              balance: amnt
            }

          var url = 'https://atm-galaxybank.herokuapp.com/galaxybank/updateAtmBalance'

          this.makeAxiosCall(url, params, function(temp, response) {
            if (response.data.result == 'ERROR') {
              temp.showAlert('warning','There was an error with updating the ATM money balance! ErrorMsg:' + response.data.errMsg)
            } else {
              temp.atm = response.data
              temp.displayPanelShow = true
              temp.operationPanelShow = false
            }
          })
         }
      },

      getATMInformation: function() {
         var params = {
             atmId: 1
          }

          var url = 'https://atm-galaxybank.herokuapp.com/galaxybank/getAtmInfo'

          this.makeAxiosCall(url, params, function(temp, response) {
            console.log("ATM info retrieving")
            if (response.data.result == 'ERROR') {
              temp.showAlert('warning','There was an error with retrieving the ATMs Information! ErrorMsg:' + response.data.errMsg)
            } else {
              temp.atm = response.data
            }
          })
      },

      cancelSelectedFieldInput: function () {
          this.atm = null
          this.user = null
          this.email = ''
          this.password = ''
          this.amount = ''
          this.showAccountBalance = false
      },
      showMenuPanel: function () {
          this.menuPanelShow = true
          this.welcomePanelShow = false
          this.operationPanelShow = false
          this.displayPanelShow = false
          this.showAccountBalance = false
      },
      showWithdrawPanel: function () {
          this.menuPanelShow = false
          this.operationPanelShow = true
      },
      showDepositPanel: function () {
          this.menuPanelShow = false
          this.operationPanelShow = true
      },
      showBalancePanel: function () {
          this.menuPanelShow = false
          this.operationPanelShow = false
          this.displayPanelShow = true
          this.showAccountBalance = true
      },
      cancelAllOperations: function () {
          this.menuPanelShow = false
          this.operationPanelShow = false
          this.welcomePanelShow = true
          this.displayPanelShow = false
          this.cancelSelectedFieldInput()
      },

      showAlert: function (type, errorMsg) {
        this.type = type
        this.errorMsg = errorMsg

        let timer = this.showAlert.timer
        if (timer) {
          clearTimeout(timer)
        }
        this.showAlert.timer = setTimeout(() => {
            this.type = null
            this.errorMsg = null
        }, 2000)

        this.elapse = 1
        let t = this.showAlert.t
        if (t) {
          clearInterval(t)
        }

        this.showAlert.t = setInterval(() => {
          if (this.elapse === 3) {
            this.elapse = 0
            clearInterval(this.showAlert.t)
          }
          else {
            this.elapse++
          }
        }, 1000)
      },


      makeAxiosCall : function (url, params, func) {
        var temp = this;

        try {
            axios.get(url, {
                params: params,
                responseType: 'json',
                timeout: 30000
              })
              .then(function (response) {
                  func(temp,response)

              })
              .catch(function (error) {
                console.log(error);
              })
           } catch (error) {
              console.log(error)
           }
      },
    }

})

