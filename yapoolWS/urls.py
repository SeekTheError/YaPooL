from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    (r'^/security/register/$', security.accountcontroller.register),
    (r'^/security/activate/(?P<code>\w+)$', security.accountcontroller.activate),
    
    )
