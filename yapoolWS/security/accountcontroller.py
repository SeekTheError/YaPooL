from django.http import HttpResponse
from django.template import RequestContext, loader
from django.shortcuts import  render_to_response
from django.conf import settings
import logging
import re;
logger = logging.getLogger(__name__)

from couchdbinterface.entities import User

def register(request) :
  '''
  Handle a Post request with the following information:
  login, password, email
  '''
  print 'receiving a request'
  #parameter retrieval
  login = request.GET['registerLogin']
  password = request.GET['registerPassword']
  email = request.GET['registerEmail']
  
  
  #parameter validation
  loginIsValid = re.match('[\w0-9]*', login) and len(login) > 3 and len(login) < 16
  passwordIsValid = len(password) >= 6 
  #TODO check with number
  emailIsValid = re.match('[\w.]*@\w*\.[\w.]*', email)
  
  logger.info(login + ' ' + password + ' ' + email)
  
  if loginIsValid and passwordIsValid and emailIsValid :
     return processFormInformation(login, password, email, request)   
  else :
    return HttpResponse('{"message" : "failure"}') 

#TODO Find a issue: during the process, sometimes the user is created in spite of an error
import hashlib
def processFormInformation(login, password, email, request) :
  activationCode = hashlib.sha1(login+email+password).hexdigest()
  u = User(name=login, email=email, password_sha=password, activationCode=activationCode)
  print u
  u = u.create()
  print u
  if u != None :
    sendActivationMail(email=email, activationCode=activationCode)
    message = 'account succesfully created'
    return HttpResponse('{"message" : "success"}')
  else :
    message = 'error: login name already taken'
    return HttpResponse('{"message" : "error"}')
  
     
from util.mailsender import sendMail    
from hashlib import sha1

def sendActivationMail(email, activationCode) :   
  subject = 'Activation mail for YaPooL!'
  message = 'Please follow this link to activate your account'
  message += '\n' + settings.ACTIVATION_LINK_BASE_URL + activationCode
  sendMail(subject, message, email)
  return code
  
  

def activate(request, code) :
  user = User(activationCode=code)
  user = user.findByActivationCode()
  if user != None:
    if user.isActivated == False :
      user.isActivated = True
      user.update()
      message = 'your account have been successfully activated'
    else :
      message = 'this account have been already activated'
  else :
    message = 'wrong activation link'
  context = {'message': message}
  return render_to_response('index.html', context , context_instance=RequestContext(request))

     
     
