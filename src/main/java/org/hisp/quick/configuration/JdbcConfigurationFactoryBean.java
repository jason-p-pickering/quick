package org.hisp.quick.configuration;

import javax.sql.DataSource;

/*
 * Copyright (c) 2004-2016, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.hisp.quick.JdbcConfiguration;
import org.hisp.quick.StatementDialect;
import org.springframework.beans.factory.FactoryBean;

/**
 * Simple configurable factory bean that provides JdbcConfiguration instances.
 * This could be injected into the StatementManager and BatchHandlerFactory
 * components. Applications may implement their own factory bean providing
 * JdbcConfiguration instances if more customized configuration is needed.
 *
 * @author Lars Helge Overland
 */
public class JdbcConfigurationFactoryBean
    implements FactoryBean<JdbcConfiguration>
{
    // -------------------------------------------------------------------------
    // Properties
    // -------------------------------------------------------------------------

    private StatementDialect dialect;

    public void setDialect( StatementDialect dialect )
    {
        this.dialect = dialect;
    }

    private String dialectName;

    public void setDialectName( String dialectName )
    {
        this.dialectName = dialectName;
    }

    private DataSource dataSource;

    public void setDataSource( DataSource dataSource )
    {
        this.dataSource = dataSource;
    }

    // -------------------------------------------------------------------------
    // InitializingBean implementation
    // -------------------------------------------------------------------------

    private JdbcConfiguration configuration;

    public void init()
    {
        JdbcConfiguration configuration = new JdbcConfiguration();

        StatementDialect _dialect = dialect != null ? dialect : StatementDialect.valueOf( dialectName );

        configuration.setDialect( _dialect );
        configuration.setDataSource( dataSource );

        this.configuration = configuration;
    }

    // -------------------------------------------------------------------------
    // FactoryBean implementation
    // -------------------------------------------------------------------------

    @Override
    public JdbcConfiguration getObject()
        throws Exception
    {
        return configuration;
    }

    @Override
    public Class<JdbcConfiguration> getObjectType()
    {
        return JdbcConfiguration.class;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }
}
