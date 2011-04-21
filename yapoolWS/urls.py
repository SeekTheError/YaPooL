#Author Remi Bouchar
from django.conf.urls.defaults import *

urlpatterns = patterns('',
    
    (r'^kuestions/register/$', 'security.registercontroller.register'),
    (r'^kuestions/register/(?P<code>\w+)$', 'security.registercontroller.activate'),
    

   
)
