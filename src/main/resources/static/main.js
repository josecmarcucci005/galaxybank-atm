new Vue({
    el: '#app',
    data: {
        card: '',
        cardLabelShow: true,
        pin: '',
        pinLabelShow: false,
        welcomePanelShow: true,
        menuPanelShow: false,
        operationPanelShow: false,
        operationType: '',
        amount: '',
        showAccountBalance: false,
        showOperationResult: false,
        displayPanelShow: false,

        type: null,
        elapse: null,
        errorMsg: null,

        customer: null,
        account: null,
        atm: null

    },

    methods: {
      changeSelectedFieldInput: function (number) {
        if (this.cardLabelShow) {
          this.card += number
        } else if (this.pinLabelShow) {
          this.pin += number
        } else if (this.operationPanelShow) {
          this.amount += number
        }
      },

      resetSelectedFieldInput: function () {
        if (this.cardLabelShow) {
          this.card = ''
        } else if (this.pinLabelShow) {
          this.pin = ''
        } else if (this.operationPanelShow) {
          this.amount = ''
        }
      },

      validateSelectedFieldInput: function () {
        if (this.cardLabelShow && this.card != '') {
          this.cardLabelShow = false
          this.pinLabelShow = true
        } else if (this.cardLabelShow && this.card == '') {
           this.showAlert('warning','You must enter a card number!')
        } else if (this.pinLabelShow && this.pin != '') {
          this.validateCard()
        } else if (this.pinLabelShow && this.pin == '') {
          this.showAlert('warning', 'You must enter a pin number!')
        } else if (this.operationPanelShow && this.amount != '') {
          //TODO: Amount Validation
          if (this.operationType == 'Withdraw') {
            this.validateWithdraw()
          } else if (this.operationType == 'Deposit') {
            this.validateDeposit()
          }
        } else if (this.operationPanelShow &&
                   (this.amount == '' || this.amount == '0')) {
          this.showAlert('warning', 'You must enter an amount number and the amount must be bigger than zero!')
        }
      },
      cancelSelectedFieldInput: function () {
          this.cardLabelShow = true
          this.pinLabelShow = false
          this.card = ''
          this.pin = ''
          this.amount = ''
          this.customer = null
          this.atm = null
      },
      showMenuPanel: function () {
          this.menuPanelShow = true
          this.welcomePanelShow = false
          this.operationPanelShow = false
          this.displayPanelShow = false
          this.showAccountBalance = false
          this.operationType = ''
          this.amount = ''
      },
      showWithdrawPanel: function () {
          this.menuPanelShow = false
          this.operationPanelShow = true
          this.operationType = 'Withdraw'
          this.pinLabelShow = false
      },
      showDepositPanel: function () {
          this.menuPanelShow = false
          this.operationPanelShow = true
          this.operationType = 'Deposit'
          this.pinLabelShow = false
      },
      showBalancePanel: function () {
          this.menuPanelShow = false
          this.operationPanelShow = false
          this.displayPanelShow = true
          this.showAccountBalance = true
          this.operationType = 'Balance'
          this.pinLabelShow = false
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

      validateCard : function () {
        var params = {
          card: this.card,
          pin: this.pin
        }

        //var url = 'http://localhost:9081/galaxybank/validateCard'
        //var url = 'https://galaxybank-atm.herokuapp.com/galaxybank/validateCard'
        var url = '/galaxybank/validateCard'

        this.makeAxiosCall(url, params, this.checkConditionsForPin)
      },

      validateWithdraw : function () {
        if (this.atm.data.balance < this.amount) {
          this.showAlert('warning','This ATM does not have enough available money to complete the withdrawal operation! Please try smaller amount or contact your bank.')
        } else if (this.account.balance < this.amount) {
          this.showAlert('warning','Insuficient funds in your account! Please try a different amount as your account balance is: ' + this.account.balance)
        } else {
          var params = {
          card: this.card,
          type: 'WITHDRAWN',
          amount: this.amount,
          atmId: '1'
          }

          //var url = 'https://galaxybank-atm.herokuapp.com/galaxybank/saveTransaction'
          //var url = 'http://localhost:9081/galaxybank/saveTransaction'
          var url = '/galaxybank/saveTransaction'

          var temp = this

          this.makeAxiosCall(url, params, function() {
            temp.displayPanelShow = true
            temp.operationPanelShow = false
          })

          this.addAmountToATMBalance((this.amount*-1))
        }
      },

      validateDeposit : function () {
          var params = {
          card: this.card,
          type: 'DEPOSIT',
          amount: this.amount,
          atmId: '1'
          }

        //var url = 'https://galaxybank-atm.herokuapp.com/galaxybank/saveTransaction'
        //var url = 'http://localhost:9081/galaxybank/saveTransaction'
        var url = '/galaxybank/saveTransaction'

        var temp = this
        this.makeAxiosCall(url, params, function() {
          temp.displayPanelShow = true
          temp.operationPanelShow = false
        })

        this.addAmountToATMBalance(this.amount)
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
                if (response.data.result == 'ERROR') {
                  console.error(response.data.errMsg)

                  temp.showAlert('warning','There was an error with the transaction! Please try again later or contact your bank.')
                } else {
                  temp.customer = response.data
                  var accounts = temp.customer.data.accounts
                  temp.account = accounts.find(function(valAcct){
                    var currCard = valAcct.cards.find(function(cardVal) {
                      return cardVal.id == temp.card
                    })
                    return currCard
                  })
                  func()
                }
              })
              .catch(function (error) {
                console.log(error);
              })
           } catch (error) {
              console.log(error)
           }
      },

      checkConditionsForPin : function() {
        var temp = this
        if (temp.customer != null && temp.customer.result == 'OK') {
            temp.showMenuPanel()
            temp.getATMInformation()
        } else if (temp.customer != null && this.customer.result == 'ERROR') {
            temp.showAlert('warning', temp.customer.errMsg)
        } else {
            temp.showAlert('warning','The service is not availabe. Please try again later or contact your bank.');
        }
      },

      getATMInformation: function() {
         var params = {
             atmId: 1
          }

          var url = '/galaxybank/getAtmInfo'

          this.makeGenAxiosCall(url, params, function(temp, response) {
            if (response.data.result == 'ERROR') {
              temp.showAlert('warning','There was an error with retrieving the ATMs Information! ErrorMsg:' + response.data.errMsg)
            } else {
              temp.atm = response.data
            }
          })
      },

      makeGenAxiosCall : function (url, params, func) {
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

      addAmountToATMBalance: function(amnt) {
            var amnt = parseFloat(this.atm.data.balance) + parseFloat(amnt)
            var params = {
              atmId: 1,
              balance: amnt
            }

          var url = '/galaxybank/updateAtmBalance'

          this.makeGenAxiosCall(url, params, function(temp, response) {
            if (response.data.result == 'ERROR') {
              console.log('There was an error with updating the ATM money balance! ErrorMsg:' + response.data.errMsg)
            } else {
              temp.atm = response.data
            }
          })

      },

    }

})