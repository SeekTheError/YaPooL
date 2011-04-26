from django.http import HttpResponse
from django.template import RequestContext, loader
from django.shortcuts import  render_to_response
from django.conf import settings
import logging
import re;
from django.utils.datastructures import  MultiValueDictKeyError
logger = logging.getLogger(__name__)

from couchdbinterface.entities import User

def register(request) :
  '''
  Handle a Post request with the following information:
  login, password, email
  '''
  print 'receiving a request'
  #parameter retrieval
  try :
    login = request.GET['registerLogin']
    password = request.GET['registerPassword']
    email = request.GET['registerEmail']
  except MultiValueDictKeyError :
    response=HttpResponse('400 - BAD URI')
    response.status_code=400
    return response
  
  #parameter validation
  loginIsValid = re.match('[\w0-9]*', login) and len(login) > 3 and len(login) < 16
  passwordIsValid = len(password) >= 6 
  #TODO check with number
  emailIsValid = re.match('[\w.]*@\w*\.[\w.]*', email)
  
  logger.info(login + ' ' + password + ' ' + email)
  
  if loginIsValid and passwordIsValid and emailIsValid :
     return processFormInformation(login, password, email, request)   
  else :
    response=HttpResponse("400")
    response['message'] = 'invalid information'
    response.status_code=400
    return response

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
    
    response=HttpResponse()
    response.status_code=200
    response['message'] = 'account successfully created'
    return response
  else :
    message = 'error: login name already taken'
    context={"message" :  "login name already taken"}
    response=HttpResponse()
    response['message'] = 'login already taken'
    response.status_code=412
    return response
  
     
from util.mailsender import sendMail    
from hashlib import sha1

def sendActivationMail(email, activationCode) :   
  subject = 'Activation mail for YaPooL!'
  message = 'Please follow this link to activate your account'
  message += '\n' + settings.ACTIVATION_LINK_BASE_URL + activationCode
  sendMail(subject, message, email)
  

def activate(request, code) :
  user = User(activationCode=code)
  user = user.findByActivationCode()
  if user != None:
    if user.isActivated == False :
      user.isActivated = True
      user.update()
      message = 'your account have been successfully activated'
      return HttpResponse('your account have been successfully activated')
    else :
      return HttpResponse('this account have already been activated')
  else :
    return HttpResponse('wrong activation link')

     
     
