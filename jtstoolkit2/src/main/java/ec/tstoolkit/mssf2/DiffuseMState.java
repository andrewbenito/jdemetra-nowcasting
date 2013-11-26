/*
* Copyright 2013 National Bank of Belgium
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
* by the European Commission - subsequent versions of the EUPL (the "Licence");
* You may not use this work except in compliance with the Licence.
* You may obtain a copy of the Licence at:
*
* http://ec.europa.eu/idabc/eupl
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the Licence is distributed on an "AS IS" basis,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and 
* limitations under the Licence.
*/
package ec.tstoolkit.mssf2;

import ec.tstoolkit.ssf2.*;
import ec.tstoolkit.data.DataBlock;
import ec.tstoolkit.design.Development;
import ec.tstoolkit.maths.matrices.Matrix;

/**
 * 
 * @author Jean Palate
 */
@Development(status = Development.Status.Alpha)
public class DiffuseMState extends State {

    /**
     *
     */
    public double fi;

    /**
     *
     */
    public DataBlock Ci;

    /**
     *
     */
    public Matrix Pi;

    /**
     * 
     * @param n
     * @param hasdata
     */
    public DiffuseMState(final int n, final boolean hasdata)
    {
	super(n, hasdata);
	Ci = new DataBlock(n);
	Pi = new Matrix(n, n);
    }

    /**
     * 
     * @param state
     */
    public void copy(final DiffuseState state)
    {
	super.copy(state);
	fi = state.fi;
	Ci = state.Ci.deepClone();
	Pi = state.Pi.clone();
    }

}
