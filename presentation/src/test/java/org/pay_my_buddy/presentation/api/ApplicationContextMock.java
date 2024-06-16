package org.pay_my_buddy.presentation.api;

import org.springframework.context.ApplicationContext;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ApplicationContextMock {


    private final ApplicationContext applicationContext = mock(ApplicationContext.class);

    public <T> void mockBean(T bean) {
        Class<T> beanClass = (Class<T>) bean.getClass();
        String beanName = bean.getClass().getName();
        doReturn(bean).when(applicationContext).getBean(beanClass);
        doReturn(beanClass).when(applicationContext).getType(beanName);
        doReturn(new String[]{beanName}).when(applicationContext).getBeanNamesForType(beanClass);

    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
