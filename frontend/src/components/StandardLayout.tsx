// LayoutWithTopBar.tsx
import * as React from 'react';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import TopBar from './TopBar';

interface Props {
    children: React.ReactNode;
}

const StandardLayout: React.FC<Props> = ({ children }) => {
    return (
        <div>
            <TopBar />
            <Container>
                <Box mt={3}> {/* marginTop for spacing after the AppBar */}
                    {children}
                </Box>
            </Container>
        </div>
    );
}

export default StandardLayout;
