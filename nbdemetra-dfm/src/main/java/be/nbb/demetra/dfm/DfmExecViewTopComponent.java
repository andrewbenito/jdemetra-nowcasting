/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.nbb.demetra.dfm;

import ec.nbdemetra.ui.DemetraUiIcon;
import ec.nbdemetra.ui.properties.OpenIdePropertySheetBeanEditor;
import ec.nbdemetra.ws.WorkspaceItem;
import ec.nbdemetra.ws.ui.WorkspaceTopComponent;
import ec.tss.Dfm.DfmDocument;
import ec.tss.Dfm.DfmProcessingFactory;
import ec.tstoolkit.algorithm.CompositeResults;
import ec.tstoolkit.algorithm.IProcessingHook;
import ec.tstoolkit.algorithm.IProcessingNode;
import ec.tstoolkit.dfm.DfmEstimationSpec;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.core.spi.multiview.CloseOperationState;
import org.netbeans.core.spi.multiview.MultiViewDescription;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.MultiViewElementCallback;
import org.openide.util.Cancellable;
import org.openide.util.ImageUtilities;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//be.nbb.demetra.dfm//DfmExecView//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "DfmExecViewTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@Messages({
    "CTL_DfmExecViewAction=DfmExecView",
    "CTL_DfmExecViewTopComponent=DfmExecView Window",
    "HINT_DfmExecViewTopComponent=This is a DfmExecView window"
})
public final class DfmExecViewTopComponent extends WorkspaceTopComponent<DfmDocument> implements MultiViewElement, MultiViewDescription {

    public DfmExecViewTopComponent() {
        this(null);
    }

    public DfmExecViewTopComponent(WorkspaceItem<DfmDocument> document) {
        super(document);
        initComponents();
        setName(Bundle.CTL_DfmExecViewTopComponent());
        setToolTipText(Bundle.HINT_DfmExecViewTopComponent());
        jEditorPane1.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setViewportView(jEditorPane1);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    void writeProperties(java.util.Properties p) {
    }

    void readProperties(java.util.Properties p) {
    }

    //<editor-fold defaultstate="collapsed" desc="MultiViewElement">
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    @Override
    public JComponent getVisualRepresentation() {
        return this;
    }

    @Override
    public JComponent getToolbarRepresentation() {
        JToolBar toolBar = new JToolBar();
        toolBar.addSeparator();
        toolBar.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton runButton = toolBar.add(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        runButton.setIcon(DemetraUiIcon.COMPILE_16);
        runButton.setDisabledIcon(ImageUtilities.createDisabledIcon(runButton.getIcon()));

        JButton edit = toolBar.add(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editEstimationSpec();
            }
        });
        edit.setText("Specification");

        return toolBar;
    }

    @Override
    public void setMultiViewCallback(MultiViewElementCallback callback) {
    }

    @Override
    public CloseOperationState canCloseElement() {
        return CloseOperationState.STATE_OK;
    }

    @Override
    public void componentActivated() {
        super.componentActivated();
    }

    @Override
    public void componentDeactivated() {
        super.componentDeactivated();
    }

    @Override
    public void componentHidden() {
        super.componentHidden();
    }

    @Override
    public void componentShowing() {
        super.componentShowing();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="MultiViewDescription">
    @Override
    public MultiViewElement createElement() {
        return this;
    }

    @Override
    public String preferredID() {
        return super.preferredID();
    }
    //</editor-fold>

    @Override
    protected String getContextPath() {
        return DfmDocumentManager.CONTEXTPATH;
    }

    private void run() {
        jEditorPane1.setText("");
        new SwingWorkerImpl().execute();
    }

    private void editEstimationSpec() {
        DfmEstimationSpec newValue = getDocument().getElement().getSpecification().getEstimationSpec().clone();
        if (OpenIdePropertySheetBeanEditor.editSheet(DfmSheets.onDfmEstimationSpec(newValue), "Edit spec", null)) {
            getDocument().getElement().getSpecification().setEstimationSpec(newValue);
            getDocument().getElement().clear();
        }
    }

    private final class SwingWorkerImpl extends SwingWorker<CompositeResults, IProcessingHook.HookInformation<IProcessingNode, DfmProcessingFactory.EstimationInfo>> implements IProcessingHook<IProcessingNode, DfmProcessingFactory.EstimationInfo> {

        private final ProgressHandle progressHandle;

        public SwingWorkerImpl() {
            this.progressHandle = ProgressHandleFactory.createHandle(getName(), new Cancellable() {
                @Override
                public boolean cancel() {
                    SwingWorkerImpl.this.cancel(false);
                    return true;
                }
            }, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    DfmExecViewTopComponent.this.open();
                    DfmExecViewTopComponent.this.requestActive();
                }
            });
            progressHandle.start();
        }

        @Override
        protected CompositeResults doInBackground() throws Exception {
            getDocument().getElement().getProcessor().register(this);
            CompositeResults rslt = getDocument().getElement().getResults();
            getDocument().getElement().getProcessor().unregister(this);
            return rslt;
        }

        @Override
        protected void done() {
            progressHandle.finish();
            DfmExecViewTopComponent.this.requestAttention(true);
        }

        @Override
        public void process(HookInformation<IProcessingNode, DfmProcessingFactory.EstimationInfo> info, boolean cancancel) {
            if (isCancelled()) {
                info.cancel = true;
            }
            publish(info);
        }

        @Override
        protected void process(List<HookInformation<IProcessingNode, DfmProcessingFactory.EstimationInfo>> chunks) {
            for (HookInformation<IProcessingNode, DfmProcessingFactory.EstimationInfo> info : chunks) {
                progressHandle.progress(info.message);
                StringBuilder txt = new StringBuilder();
                txt.append(info.source.getName()).append('\t')
                        .append(info.message).append('\t').append(info.information.loglikelihood);
                txt.append("\r\n");
                final String msg = jEditorPane1.getText() + txt.toString();
                jEditorPane1.setText(msg);
                jEditorPane1.repaint();
            }
        }
    }
}